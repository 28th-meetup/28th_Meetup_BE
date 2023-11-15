package com.kusitms.jipbap.order;

import com.kusitms.jipbap.food.Food;
import com.kusitms.jipbap.food.FoodRepository;
import com.kusitms.jipbap.food.exception.FoodNotExistsException;
import com.kusitms.jipbap.order.dto.OrderDto;
import com.kusitms.jipbap.order.dto.OrderFoodRequestDto;
import com.kusitms.jipbap.order.exception.OrderNotExistsByOrderStatusException;
import com.kusitms.jipbap.order.exception.OrderNotExistsException;
import com.kusitms.jipbap.order.exception.OrderNotFoundException;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.UserRepository;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    public OrderDto orderFood(OrderFoodRequestDto dto) {
        User user = userRepository.findById(dto.getUser()).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));
        Food food = foodRepository.findById(dto.getFood()).orElseThrow(()-> new FoodNotExistsException("해당 음식은 유효하지 않습니다."));

        Order order = orderRepository.save(
                Order.builder()
                        .user(user)
                        .food(food)
                        .orderCount(dto.getOrderCount())
                        .totalPrice(dto.getTotalPrice())
                        .regionId(user.getGlobalRegion().getId())
                        .status(OrderStatus.PENDING)
                        .build()
        );
        return new OrderDto(order);
    }

    public OrderDto getOrderDetail(Long orderId) {
        Order order = orderRepository.findById(orderId).
                orElseThrow(()-> new OrderNotFoundException("해당 주문" + orderId + "는 유효하지 않습니다."));
        return new OrderDto(order);
    }

    public List<OrderDto> getStoreOrderHistoryByOrderStatus(Long storeId, String orderStatus) {
        OrderStatus status = OrderStatus.fromString(orderStatus);
        List<Order> orders = orderRepository.findByFood_Store_IdAndStatus(storeId, status)
                .orElseThrow(() -> new OrderNotExistsByOrderStatusException("해당 가게의 주문상태에 따른 주문 내역이 존재하지 않습니다."));
        if (orders.isEmpty()) {
            throw new OrderNotExistsByOrderStatusException("해당 가게의 주문상태에 따른 주문 내역이 존재하지 않습니다.");
        }

        return orders.stream().map(OrderDto::new).collect(Collectors.toList());
    }
    
}
