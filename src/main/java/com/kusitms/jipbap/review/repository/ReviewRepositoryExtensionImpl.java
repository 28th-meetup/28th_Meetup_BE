package com.kusitms.jipbap.review.repository;

import com.kusitms.jipbap.review.model.entity.Review;
import com.kusitms.jipbap.store.model.entity.Store;
import com.kusitms.jipbap.user.model.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.kusitms.jipbap.food.model.entity.QFood.food;
import static com.kusitms.jipbap.order.model.entity.QOrder.order;
import static com.kusitms.jipbap.review.model.entity.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryExtensionImpl implements ReviewRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> findAllReviewsByUser(User user) {
        return queryFactory.selectFrom(review)
                .join(review.order, order)
                .where(order.user.eq(user))
                .fetch();
    }

    @Override
    public List<Review> findAllReviewsByStore(Store store) {
        return queryFactory.selectFrom(review)
                .join(review.order, order)
                .where(food.store.eq(store))
                .fetch();
    }

}