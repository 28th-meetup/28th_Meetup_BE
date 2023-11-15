package com.kusitms.jipbap.order;

import com.kusitms.jipbap.food.Category;
import com.kusitms.jipbap.food.Food;
import com.kusitms.jipbap.food.FoodRepository;
import com.kusitms.jipbap.food.dto.CategoryDto;
import com.kusitms.jipbap.food.exception.FoodNotExistsException;
import com.kusitms.jipbap.order.dto.OrderDto;
import com.kusitms.jipbap.order.dto.OrderFoodRequestDto;
import com.kusitms.jipbap.order.exception.OrderNotExistsException;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.UserRepository;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotExistsException("해당 주문은 유효하지 않습니다."));
        return new OrderDto(order);
    }
}
