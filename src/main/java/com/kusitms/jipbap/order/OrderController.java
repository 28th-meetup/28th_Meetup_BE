package com.kusitms.jipbap.order;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.order.dto.*;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "음식 주문하기")
    @PostMapping("/food")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<OrderFoodResponse> orderFood(@Auth AuthInfo authInfo, @Valid @RequestBody OrderFoodRequest dto) {
        return new CommonResponse<>(orderService.orderFood(authInfo.getEmail(), dto));
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
    public CommonResponse<String> processOrder(
            @Auth AuthInfo authInfo,
            @PathVariable Long orderId,
            @RequestParam("new-status") String newStatus) {
        orderService.processOrder(authInfo.getEmail(), orderId, newStatus);
        return new CommonResponse<>("주문 상태 변경에 성공했습니다.");
    }

    @Operation(summary = "구매자의 주문 내역 확인하기")
    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<List<OrderHistoryResponse>> getMyOrderHistory(@Auth AuthInfo authInfo) {
        return new CommonResponse<>(orderService.getMyOrderHistory(authInfo.getEmail()));
    }

    @Operation(summary = "주문내역 중 진행 중인 메뉴 조회하기")
    @GetMapping("/processing")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<StoreProcessingResponse> getStoreProcessingOrder(@Auth AuthInfo authInfo) {
        return new CommonResponse<>(orderService.getStoreProcessingOrder(authInfo.getEmail()));
    }

}
