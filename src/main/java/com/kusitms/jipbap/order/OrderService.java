package com.kusitms.jipbap.order;

import com.kusitms.jipbap.food.Food;
import com.kusitms.jipbap.food.FoodOption;
import com.kusitms.jipbap.food.FoodOptionRepository;
import com.kusitms.jipbap.food.FoodRepository;
import com.kusitms.jipbap.food.exception.FoodNotExistsException;
import com.kusitms.jipbap.food.exception.FoodOptionNotExistsException;
import com.kusitms.jipbap.notification.FCMNotificationService;
import com.kusitms.jipbap.notification.FCMRequestDto;
import com.kusitms.jipbap.order.dto.*;
import com.kusitms.jipbap.order.exception.*;
import com.kusitms.jipbap.store.Store;
import com.kusitms.jipbap.store.StoreRepository;
import com.kusitms.jipbap.store.exception.StoreNotExistsException;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.UserRepository;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final FoodOptionRepository foodOptionRepository;
    private final StoreRepository storeRepository;
    private final FCMNotificationService fcmNotificationService;

    @Transactional
    public OrderFoodResponse orderFood(String email, OrderFoodRequest dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        Store store = storeRepository.findById(dto.getStore())
                .orElseThrow(()-> new StoreNotExistsException("해당 가게는 존재하지 않습니다."));

        Order order = orderRepository.save(
                Order.builder()
                        .user(user)
                        .store(store)
                        .totalPrice(dto.getTotalPrice())
                        .totalCount(dto.getTotalCount())
                        .regionId(user.getGlobalRegion().getId())
                        .selectedOption(dto.getSelectedOption())
                        .status(OrderStatus.PENDING)
                        .build()
        );

        List<OrderDetail> orderedFoodList = saveOrderFoodDetail(order.getId(), dto.getOrderFoodDetailList());
        order.setOrderDetail(orderedFoodList);

        // 알림 등 로직 추가 가능
        FCMRequestDto fcmRequestDto = new FCMRequestDto(store.getOwner().getId(), "주문이 들어왔습니다", "주문을 확인해주세요.");
        String ans = fcmNotificationService.sendNotificationByToken(fcmRequestDto);
        log.info(ans);
        orderRepository.save(order);

        return new OrderFoodResponse(order);
    }

    @Transactional
    public List<OrderDetail> saveOrderFoodDetail(Long orderId, List<OrderFoodDetailRequest> orderFoodDetailList) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("주문 아이디를 찾을 수 없습니다."));
        return orderFoodDetailList.stream()
                .map(item -> {
                    Food food = foodRepository.findById(item.getFoodId())
                                    .orElseThrow(()-> new FoodNotExistsException("해당 음식은 유효하지 않습니다."));
                    FoodOption foodOption = foodOptionRepository.findById(item.getFoodOptionId())
                            .orElseThrow(()-> new FoodOptionNotExistsException("해당 음식 옵션은 유효하지 않습니다."));
                    OrderDetail orderDetail = OrderDetail.builder()
                            .food(food)
                            .foodOption(foodOption) // 수정
                            .orderCount(item.getOrderCount())
                            .orderAmount(item.getOrderAmount())
                            .order(order)
                            .build();
                    orderDetailRepository.save(orderDetail);
                    return orderDetail;
                })
                .collect(Collectors.toList());
    }

    public OrderDto getOrderDetail(Long orderId) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(()-> new OrderNotFoundException("해당 주문" + orderId + "는 유효하지 않습니다."));
        return new OrderDto(order);
    }

    public OwnerOrderStatusResponse getStoreOrderHistoryByOrderStatus(String email, String orderStatus) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        Store store = storeRepository.findByOwner(user)
                .orElseThrow(() -> new StoreNotExistsException("해당 유저의 가게를 찾을 수 없습니다."));

        OrderStatus status = OrderStatus.fromString(orderStatus);

        List<Order> orderList = orderRepository.findByStore_IdAndStatus(store.getId(), status)
                .orElseThrow(() -> new OrderNotExistsByOrderStatusException("해당 가게의 주문상태에 따른 주문 내역이 존재하지 않습니다."));

        List<OrderPreviewResponse> orderPreviewResponses = orderList.stream()
                .map(OrderPreviewResponse::new)
                .collect(Collectors.toList());

        if (orderList.isEmpty()) {
            return new OwnerOrderStatusResponse(0, Collections.emptyList());
        }
        return new OwnerOrderStatusResponse(orderPreviewResponses.size(), orderPreviewResponses);
    }

    @Transactional
    public void processOrder(String email, Long orderId, String status) {
        User seller = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다.")); //현재 사용자 정보

        OrderStatus newStatus = OrderStatus.fromString(status);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("주문을 찾을 수 없습니다. orderId: " + orderId));

        if (!seller.getId().equals(order.getStore().getOwner().getId())) {
            throw new UnauthorizedAccessException("주문 상태를 변경할 권한이 없습니다.");
        }

        if(order.getStatus() == newStatus){
            throw new OrderStatusAlreadyExistsException("이미 해당 주문 상태입니다.");
        }

        order.setStatus(newStatus); // 주문 상태 변경
        orderRepository.save(order);

        User buyer = order.getUser();
        if(newStatus.equals(OrderStatus.ACCEPTED)) { //판매자가 주문을 수락함
            FCMRequestDto dto = new FCMRequestDto(buyer.getId(), "가게가 주문을 수락했습니다.", "맛있는 한식 집밥이 곧 찾아갑니다!");
            String ans = fcmNotificationService.sendNotificationByToken(dto);
            log.info("판매자가 주문을 수락, 구매자에게 알림 전송 결과: " + ans);
        }
        else if(newStatus.equals(OrderStatus.REJECTED)) { //판매자가 주문을 취소함
            FCMRequestDto dto = new FCMRequestDto(buyer.getId(), "가게가 주문을 취소했습니다.", "다른 상품을 주문해 주세요.");
            String ans = fcmNotificationService.sendNotificationByToken(dto);
            log.info("판매자가 주문을 거절, 구매자에게 알림 전송 결과: " + ans);

        }
        else if(newStatus.equals(OrderStatus.COMPLETED)) { //판매자가 주문을 완료함
            FCMRequestDto dto = new FCMRequestDto(buyer.getId(), "음식이 완료되었습니다.", "한식 집밥, 맛있게 즐기세요!");
            String ans = fcmNotificationService.sendNotificationByToken(dto);
            log.info("판매자가 주문을 완료, 구매자에게 알림 전송 결과: " + ans);

        }
    }

    public List<OrderHistoryResponse> getMyOrderHistory(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        List<Order> myOrderList = orderRepository.findByUser_Id(user.getId())
                .orElse(Collections.emptyList());

        List<OrderHistoryResponse> orderFoodResponseList = myOrderList
                .stream()
                .map(OrderHistoryResponse::new)
                .collect(Collectors.toList());

        return orderFoodResponseList;
    }

    public StoreProcessingResponse getStoreProcessingOrder(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        Store store = storeRepository.findByOwner(user)
                .orElseThrow(() -> new StoreNotExistsException("해당 유저의 가게를 찾을 수 없습니다."));

        //전체 주문내역에서 해당 가게에 속하는 주문내역만 가져오기
        List<Order> orderList = orderRepository.findByStore_IdAndStatus(store.getId(), OrderStatus.ACCEPTED)
                .orElseThrow(() -> new OrderNotExistsByOrderStatusException("해당 가게의 주문상태에 따른 주문 내역이 존재하지 않습니다."));

        //주문내역 중에서 음식별로 묶기
        List<OrderDetail> orderDetailList = orderList.stream()
                .flatMap(order -> order.getOrderDetail().stream())
                .collect(Collectors.toList());

        //음식 옵션별로 묶어서 음식별로 묶기
        List<ProcessingFoodResponse> processingFoodResponseList = orderDetailList.stream()
                .collect(Collectors.groupingBy(OrderDetail::getFood))
                .entrySet().stream()
                .map(entry -> {
                    Food food = entry.getKey();
                    List<ProcessingFoodDetailResponse> processingFoodDetailResponseList = entry.getValue().stream()
                            .collect(Collectors.groupingBy(OrderDetail::getFoodOption,
                                    Collectors.summingLong(OrderDetail::getOrderCount)))
                            .entrySet().stream()
                            .map(entry2 -> new ProcessingFoodDetailResponse(
                                    entry2.getKey().getId(),
                                    entry2.getKey().getName(),
                                    entry2.getValue()))
                            .collect(Collectors.toList());
                    long totalOrderCount = processingFoodDetailResponseList.stream()
                            .mapToLong(ProcessingFoodDetailResponse::getOrderCount)
                            .sum();
                    return new ProcessingFoodResponse(food.getId(), food.getName(), totalOrderCount, processingFoodDetailResponseList);
                })
                .collect(Collectors.toList());

        if (orderList.isEmpty()) {
            return new StoreProcessingResponse(0, Collections.emptyList());
        }
        return new StoreProcessingResponse(orderList.size(), processingFoodResponseList);
    }
}
