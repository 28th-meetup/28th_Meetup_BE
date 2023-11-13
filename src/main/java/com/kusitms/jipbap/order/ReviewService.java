package com.kusitms.jipbap.order;

import com.kusitms.jipbap.order.dto.GetRegisteredReviewsResponseDto;
import com.kusitms.jipbap.order.dto.RegisterReviewRequestDto;
import com.kusitms.jipbap.order.dto.ReviewDto;
import com.kusitms.jipbap.order.exception.OrderNotExistsException;
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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReviewDto registerReview(String email, RegisterReviewRequestDto dto) {
        userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));
        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(()-> new OrderNotExistsException("orderId: "+dto.getOrderId()+"에 해당하는 주문이 존재하지 않습니다."));

        Review review = reviewRepository.save(new Review(null, order, dto.getRating(), dto.getMessage()));

        return new ReviewDto(review.getId(), review.getOrder().getId(), review.getRating(), review.getMessage());
    }

    @Transactional
    public GetRegisteredReviewsResponseDto getUserRegisteredReviews(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("유저 정보가 존재하지 않습니다."));

        List<Review> reviews = reviewRepository.findAllReviewsByUser(user);

        return new GetRegisteredReviewsResponseDto(
                reviews.stream()
                .map(r -> new ReviewDto(r.getId(), r.getOrder().getId(), r.getRating(), r.getMessage()))
                .collect(Collectors.toList())
        );
    }

    @Transactional
    public GetRegisteredReviewsResponseDto getStoreRegisteredReviews(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(()-> new StoreNotExistsException("storeId: "+storeId+"에 해당하는 가게가 존재하지 않습니다."));

        List<Review> reviews = reviewRepository.findAllReviewsByStore(store);

        return new GetRegisteredReviewsResponseDto(
                reviews.stream()
                .map(r -> new ReviewDto(r.getId(), r.getOrder().getId(), r.getRating(), r.getMessage()))
                .collect(Collectors.toList())
        );
    }
}
