package com.kusitms.jipbap.order;

import com.kusitms.jipbap.food.Food;
import com.kusitms.jipbap.food.FoodOption;
import com.kusitms.jipbap.food.FoodOptionRepository;
import com.kusitms.jipbap.food.FoodRepository;
import com.kusitms.jipbap.food.exception.FoodNotExistsException;
import com.kusitms.jipbap.food.exception.FoodOptionNotExistsException;
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

        return new OrderFoodResponse(order);
    }

    @Transactional
    public List<OrderDetail> saveOrderFoodDetail(Long orderId, List<OrderFoodDetailRequest> orderFoodDetailList){
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

        if (orderList.isEmpty()) {
            throw new OrderNotExistsByOrderStatusException("해당 가게의 주문상태에 따른 주문 내역이 존재하지 않습니다.");
        }

        List<OrderPreviewResponse> orderPreviewResponses = orderList.stream()
                .map(OrderPreviewResponse::new)
                .collect(Collectors.toList());

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
        // 알림 등 로직 추가 가능

        orderRepository.save(order);
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

}
