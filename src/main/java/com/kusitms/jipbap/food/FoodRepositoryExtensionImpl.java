package com.kusitms.jipbap.food;

import com.kusitms.jipbap.common.utils.QueryDslUtils;
import com.kusitms.jipbap.order.Review;
import com.kusitms.jipbap.order.ReviewRepositoryExtension;
import com.kusitms.jipbap.store.Store;
import com.kusitms.jipbap.user.User;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.kusitms.jipbap.food.QFood.food;
import static com.kusitms.jipbap.order.QOrder.order;
import static com.kusitms.jipbap.order.QReview.review;
import static com.kusitms.jipbap.store.QStore.store;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class FoodRepositoryExtensionImpl implements FoodRepositoryExtension {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Food> searchByNameOrderBySort(User user, Pageable pageable, String keyword, String standard, String order) {

        List<OrderSpecifier<?>> orderSpecifiers = getAllOrderSpecifiersByPageable(pageable);

        return queryFactory.selectFrom(food)
                .where(
                        containsKeyword(keyword)
                )
                .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                .fetch();
    }

    private BooleanExpression containsKeyword(String keyword) {
        if(keyword == null) {
            return null;
        }
        return food.name.contains(keyword);
    }

    private List<OrderSpecifier<?>> getAllOrderSpecifiersByPageable(Pageable pageable) {

        List<OrderSpecifier<?>> orderSpecifierList = new ArrayList<>();

        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    // 기본 정렬조건: 추천순
                    case "bookmark": // 추천순
                        orderSpecifierList.add(QueryDslUtils.getSortedColumn(direction, store, "bookmarkCount"));
                        orderSpecifierList.add(QueryDslUtils.getSortedColumn(Order.DESC, store, "id"));
                        break;
                    case "review": // 후기순
                        orderSpecifierList.add(QueryDslUtils.getSortedColumn(direction, store, "reviewCount"));
                        orderSpecifierList.add(QueryDslUtils.getSortedColumn(Order.DESC, store, "id"));
                        break;
                    case "rate": // 평점순
                        orderSpecifierList.add(QueryDslUtils.getSortedColumn(direction, store, "avgRate"));
                        orderSpecifierList.add(QueryDslUtils.getSortedColumn(Order.DESC, store, "id"));
                        break;
                    case "id": // 최신순
                        orderSpecifierList.add(QueryDslUtils.getSortedColumn(direction, store, "id"));
                        break;
                    default:
                        break;
                }
            }
        }

        return orderSpecifierList;
    }
}