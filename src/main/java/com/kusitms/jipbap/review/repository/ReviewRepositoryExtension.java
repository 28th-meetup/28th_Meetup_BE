package com.kusitms.jipbap.review.repository;

import com.kusitms.jipbap.review.model.entity.Review;
import com.kusitms.jipbap.store.model.entity.Store;
import com.kusitms.jipbap.user.model.entity.User;

import java.util.List;

public interface ReviewRepositoryExtension {
    List<Review> findAllReviewsByUser(User user);
    List<Review> findAllReviewsByStore(Store store);
}
