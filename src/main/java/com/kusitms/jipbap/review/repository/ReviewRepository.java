package com.kusitms.jipbap.review.repository;

import com.kusitms.jipbap.review.model.entity.Review;
import com.kusitms.jipbap.store.model.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository <Review, Long>, ReviewRepositoryExtension {
    List<Review> findAllReviewsByOrder_Store(Store store);
}
