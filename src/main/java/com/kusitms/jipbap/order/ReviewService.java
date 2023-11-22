package com.kusitms.jipbap.order;

import com.amazonaws.services.s3.AmazonS3;
import com.kusitms.jipbap.common.exception.S3RegisterFailureException;
import com.kusitms.jipbap.common.utils.S3Utils;
import com.kusitms.jipbap.notification.FCMNotificationService;
import com.kusitms.jipbap.notification.FCMRequestDto;
import com.kusitms.jipbap.order.dto.GetRegisteredReviewsResponseDto;
import com.kusitms.jipbap.order.dto.RegisterReviewRequestDto;
import com.kusitms.jipbap.order.dto.ReviewDto;
import com.kusitms.jipbap.order.exception.AlreadyExistsReviewException;
import com.kusitms.jipbap.order.exception.OrderNotExistsException;
import com.kusitms.jipbap.store.Store;
import com.kusitms.jipbap.store.StoreRepository;
import com.kusitms.jipbap.store.exception.StoreNotExistsException;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.UserRepository;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final FCMNotificationService fcmNotificationService;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public ReviewDto registerReview(String email, RegisterReviewRequestDto dto, MultipartFile image) {
        userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(()-> new OrderNotExistsException("orderId: "+dto.getOrderId()+"에 해당하는 주문이 존재하지 않습니다."));

        if(order.getReview() != null){
            throw new AlreadyExistsReviewException("이미 리뷰를 작성한 주문입니다.");
        }

        Store store = order.getStore();

        String imageUri = null;
        // 이미지가 null이 아닌 경우 s3 업로드
        if(image!=null) {
            try {
                imageUri = S3Utils.saveFile(amazonS3, bucket, image);
            } catch (IOException e) {
                throw new S3RegisterFailureException("리뷰 이미지 저장 중 오류가 발생했습니다.");
            }
        }

        // 리뷰 작성
        Review review = reviewRepository.save(new Review(null, order, dto.getRating(), dto.getMessage(), imageUri));

        store.increaseReviewCount(); // 리뷰 개수 업데이트
        store.updateAvgRate(Double.parseDouble(String.valueOf(dto.getRating()))); // 평점 업데이트

        // 주문내역에 리뷰 추가 및 판매자에게 리뷰 작성되었다는 알림 전송
        order.setReview(review);
        FCMRequestDto fcmRequestDto = new FCMRequestDto(store.getOwner().getId(), "고객이 리뷰를 작성했습니다.", "내용을 확인해 보시겠어요?");
        String ans = fcmNotificationService.sendNotificationByToken(fcmRequestDto);
        log.info("구매자가 리뷰 작성 완료 알림 전송: " + ans);

        //알림 데이터베이스에 저장

        return new ReviewDto(review.getId(), review.getOrder().getId(), review.getOrder().getUser().getUsername(), review.getCreatedAt().toString(), review.getOrder().getOrderDetail().get(0).getFood().getName(), review.getRating(), review.getMessage(), review.getImage());
    }

    @Transactional
    public GetRegisteredReviewsResponseDto getUserRegisteredReviews(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("유저 정보가 존재하지 않습니다."));

        List<Review> reviews = reviewRepository.findAllReviewsByUser(user);

        return new GetRegisteredReviewsResponseDto(
                reviews.stream()
                .map(r -> new ReviewDto(r.getId(), r.getOrder().getId(), r.getOrder().getUser().getUsername(), r.getCreatedAt().toString(), r.getOrder().getOrderDetail().get(0).getFood().getName(), r.getRating(), r.getMessage(), r.getImage()))
                .collect(Collectors.toList())
        );
    }

    @Transactional
    public GetRegisteredReviewsResponseDto getStoreRegisteredReviews(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()-> new StoreNotExistsException("storeId: "+storeId+"에 해당하는 가게가 존재하지 않습니다."));
        List<Review> reviews = reviewRepository.findAllReviewsByOrder_Store(store);

        return new GetRegisteredReviewsResponseDto(
                reviews.stream()
                .map(r -> new ReviewDto(r.getId(), r.getOrder().getId(), r.getOrder().getUser().getUsername(), r.getCreatedAt().toString(), r.getOrder().getOrderDetail().get(0).getFood().getName(), r.getRating(), r.getMessage(), r.getImage()))
                .collect(Collectors.toList())
        );
    }
}
