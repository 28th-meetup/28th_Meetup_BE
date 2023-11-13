package com.kusitms.jipbap.order;

import com.kusitms.jipbap.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.kusitms.jipbap.order.QOrder.order;
import static com.kusitms.jipbap.order.QReview.review;

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

}