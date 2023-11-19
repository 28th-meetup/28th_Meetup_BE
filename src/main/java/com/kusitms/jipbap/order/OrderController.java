package com.kusitms.jipbap.order;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.order.dto.OrderDto;
import com.kusitms.jipbap.order.dto.OrderFoodRequest;
import com.kusitms.jipbap.order.dto.OrderFoodResponse;
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
    public CommonResponse<OrderFoodResponse> orderFood(@Valid @RequestBody OrderFoodRequest dto) {
        return new CommonResponse<>(orderService.orderFood(dto));
    }

    @Operation(summary = "주문 내역 확인하기")
    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<OrderDto> getOrderDetail(@PathVariable Long orderId) {
        return new CommonResponse<>(orderService.getOrderDetail(orderId));
    }

    @Operation(summary = "주문내역의 주문 상태 변경하기")
    @PutMapping("/{orderId}/process")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<String> processOrder(@PathVariable Long orderId, @RequestParam("new-status") String newStatus) {
        orderService.processOrder(orderId, newStatus);
        return new CommonResponse<>("주문 상태 변경에 성공했습니다.");
    }

}
