package com.kusitms.jipbap.store;

import com.kusitms.jipbap.common.utils.QueryDslUtils;
import com.kusitms.jipbap.store.dto.StoreDetailResponseDto;
import com.kusitms.jipbap.store.dto.StoreDto;
import com.kusitms.jipbap.user.User;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.kusitms.jipbap.store.QStore.store;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
public class StoreRepositoryExtensionImpl implements StoreRepositoryExtension{

    private final JPAQueryFactory queryFactory;
    private final StoreBookmarkRepository storeBookmarkRepository;

    /**
     * 페이지네이션 적용 키워드로 store 검색하기
     */
    @Override
    public Slice<StoreDetailResponseDto> searchByKeywordOrderBySort(User user, Pageable pageable, String keyword, String standard, String order, Long lastId) {

        List<OrderSpecifier<?>> orderSpecifiers = getAllOrderSpecifiersByPageable(pageable);

        List<Store> storeList = queryFactory.selectFrom(store)
                .where(
                    lastStore(pageable, orderSpecifiers, lastId),
                    containsKeyword(keyword)
                )
                .limit(pageable.getPageSize()+1)
                .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                .fetch();

        List<StoreDetailResponseDto> dtoList = new ArrayList<>();

        for(Store s: storeList) {
            String[] strArr = new String[3];
            strArr[0] = s.getImage();
            strArr[1] = s.getImage2();
            strArr[2] = s.getImage3();
            dtoList.add(new StoreDetailResponseDto(
                    new StoreDto(
                        s.getId(),
                        s.getName(),
                        s.getDescription(),
                        s.getKoreanYn(),
                        s.getAvgRate(),
                        s.getMinOrderAmount(),
                        strArr
                    ),
                    isUserBookmarkedStore(user, s),
                    s.getFoodChangeYn()
            ));
        }

        // 무한스크롤을 위한 길이 확인
        boolean hasNext = false;
        if(storeList.size() > pageable.getPageSize()) {
            dtoList.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(dtoList, pageable, hasNext);
    }

    /**
     * 페이지네이션 미적용 가게 조회
     */
    @Override
    public List<Store> searchByNameOrderBySort(User user, Pageable pageable, String keyword, String standard, String order) {

        List<OrderSpecifier<?>> orderSpecifiers = getAllOrderSpecifiersByPageable(pageable);
        Long globalRegionId = user.getGlobalRegion().getId();

        return queryFactory.selectFrom(store)
                .where(
                        containsKeyword(keyword),
                        isUserStoreRegionMatches(globalRegionId)
                )
                .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                .fetch();
    }

    // user가 즐겨찾기한 store인지 검사
    private Boolean isUserBookmarkedStore(User user, Store store) {
        return storeBookmarkRepository.existsByUserAndStore(user, store);
    }

    private BooleanExpression isUserStoreRegionMatches(Long regionId) {
        return store.globalRegion.id.eq(regionId);
    }

    // no-offset 방식 처리하는 메서드
    // 정렬조건에 따라서 다음에 나와야 할 친구들을 구함
    private BooleanExpression lastStore(Pageable pageable, List<OrderSpecifier<?>> specifiers, Long id) {
        if(id==null) return null;

        Store stdStore = queryFactory.selectFrom(store)
                .where(store.id.eq(id))
                .fetchFirst();

        for (Sort.Order order : pageable.getSort()) {
            if(order.getDirection().isAscending()) {
                switch (order.getProperty()) {
                    case "bookmark" : // 추천순
                        return store.bookmarkCount.goe(stdStore.getBookmarkCount()).and(store.id.lt(stdStore.getId()));
                    case "review" : // 후기순
                        return store.reviewCount.goe(stdStore.getReviewCount()).and(store.id.lt(stdStore.getId()));
                    case "rate" : // 평점순
                        return store.rateCount.goe(stdStore.getRateCount()).and(store.id.lt(stdStore.getId()));
                    case "id" : // 최신순
                        return store.id.gt(stdStore.getId());
                    default :
                        return null;
                }
            } else {
                switch (order.getProperty()) {
                    case "bookmark" : // 추천순
                        return store.bookmarkCount.loe(stdStore.getBookmarkCount()).and(store.id.lt(stdStore.getId()));
                    case "review" : // 후기순
                        return store.reviewCount.loe(stdStore.getReviewCount()).and(store.id.lt(stdStore.getId()));
                    case "rate" : // 평점순
                        return store.rateCount.loe(stdStore.getRateCount()).and(store.id.lt(stdStore.getId()));
                    case "id" : // 최신순
                        return store.id.lt(stdStore.getId());
                    default :
                        return null;
                }
            }
        }

        return null;
    }

    private BooleanExpression containsKeyword(String keyword) {
        if(keyword == null) {
            return null;
        }
        return store.name.contains(keyword);
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