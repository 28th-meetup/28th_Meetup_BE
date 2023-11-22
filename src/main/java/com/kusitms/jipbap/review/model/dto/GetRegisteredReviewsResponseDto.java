package com.kusitms.jipbap.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRegisteredReviewsResponseDto {
    private List<ReviewDto> reviewList;
}
