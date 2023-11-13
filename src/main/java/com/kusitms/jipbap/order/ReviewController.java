package com.kusitms.jipbap.order;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.order.dto.GetRegisteredReviewsResponseDto;
import com.kusitms.jipbap.order.dto.RegisterReviewRequestDto;
import com.kusitms.jipbap.order.dto.ReviewDto;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "주문 리뷰 작성")
    @PostMapping
    public CommonResponse<ReviewDto> registerReview(@Auth AuthInfo authInfo, @RequestBody RegisterReviewRequestDto dto) {
        return new CommonResponse<>(reviewService.registerReview(authInfo.getEmail(), dto));
    }

    @Operation(summary = "유저가 작성한 리뷰 모아보기")
    @GetMapping
    public CommonResponse<GetRegisteredReviewsResponseDto> getRegisteredReviews(@Auth AuthInfo authInfo) {
        return new CommonResponse<>(reviewService.getRegisteredReviews(authInfo.getEmail()));
    }
}
