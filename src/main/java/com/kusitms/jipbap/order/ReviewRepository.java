package com.kusitms.jipbap.order;

import com.kusitms.jipbap.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository <Review, Long>, ReviewRepositoryExtension {
    List<Review> findAllReviewsByOrder_Store(Store store);
}
