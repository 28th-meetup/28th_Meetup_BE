package com.kusitms.jipbap.order;

import com.kusitms.jipbap.user.User;

import java.util.List;

public interface ReviewRepositoryExtension {
    List<Review> findAllReviewsByUser(User user);

}
