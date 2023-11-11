package com.kusitms.jipbap.order;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.food.dto.CategoryDto;
import com.kusitms.jipbap.food.dto.RegisterCategoryRequestDto;
import com.kusitms.jipbap.order.dto.OrderDto;
import com.kusitms.jipbap.order.dto.OrderFoodRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "음식 주문하기")
    @PostMapping("/food")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<OrderDto> orderFood(@Valid @RequestBody OrderFoodRequestDto dto) {
        return new CommonResponse<>(orderService.orderFood(dto));
    }

}
