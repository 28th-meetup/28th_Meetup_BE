package com.kusitms.jipbap.review.controller;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.review.service.ReviewService;
import com.kusitms.jipbap.review.model.dto.GetRegisteredReviewsResponseDto;
import com.kusitms.jipbap.order.model.dto.RegisterReviewRequestDto;
import com.kusitms.jipbap.review.model.dto.ReviewDto;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "주문 리뷰 작성")
    @PostMapping
    public CommonResponse<ReviewDto> registerReview(
            @Auth AuthInfo authInfo,
            @RequestPart(value = "dto") RegisterReviewRequestDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return new CommonResponse<>(reviewService.registerReview(authInfo.getEmail(), dto, image));
    }

    @Operation(summary = "유저가 작성한 리뷰 모아보기")
    @GetMapping
    public CommonResponse<GetRegisteredReviewsResponseDto> getUserRegisteredReviews(@Auth AuthInfo authInfo) {
        return new CommonResponse<>(reviewService.getUserRegisteredReviews(authInfo.getEmail()));
    }

    @Operation(summary = "가게 리뷰 모두보기")
    @GetMapping("/{storeId}")
    public CommonResponse<GetRegisteredReviewsResponseDto> getStoreRegisteredReviews(@PathVariable Long storeId) {
        return new CommonResponse<>(reviewService.getStoreRegisteredReviews(storeId));
    }
}
