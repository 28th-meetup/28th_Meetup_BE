package com.kusitms.jipbap.store;

import com.kusitms.jipbap.common.QueryDslUtil;
import com.kusitms.jipbap.store.dto.SearchStoreResponseDto;
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

    @Override
    public Slice<SearchStoreResponseDto> searchByKeywordOrderBySort(User user, Pageable pageable, String keyword, String standard, String order, Long lastId) {

        List<OrderSpecifier> orderSpecifiers = getAllOrderSpecifiers(pageable);

        List<Store> storeList = queryFactory.selectFrom(store)
                .where(
                    ltId(lastId),
                    containsKeyword(keyword)
                )
                .limit(pageable.getPageSize()+1)
                .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                .fetch();

        List<SearchStoreResponseDto> dtoList = new ArrayList<>();

        for(Store s: storeList) {
            dtoList.add(new SearchStoreResponseDto(
                    s.getId(),
                    s.getName(),
                    s.getDescription(),
                    s.getKoreanYn(),
                    s.getAvgRate(),
                    s.getMinOrderAmount(),
                    s.getImage(),
                    isUserBookmarkedStore(user, s),
                    getLastId(lastId)
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

    // lastId가 null이라면 현재 가장 마지막에 추가된 store id 반환
    private Long getLastId(Long lastId) {
        if(lastId == null) {
            return queryFactory.select(store.id)
                    .from(store)
                    .limit(1)
                    .orderBy(store.id.desc())
                    .fetchFirst();
        }
        return lastId;
    }

    // user가 즐겨찾기한 store인지 검사
    private Boolean isUserBookmarkedStore(User user, Store store) {
        return storeBookmarkRepository.existsByUserAndStore(user, store);
    }

    // no-offset 방식 처리하는 메서드
    private BooleanExpression ltId(Long id) {
        if(id == null) {
            return null;
        }
        return store.id.lt(id);
    }

    private BooleanExpression containsKeyword(String keyword) {
        if(keyword == null) {
            return null;
        }
        return store.name.contains(keyword);
    }

    private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {

        List<OrderSpecifier> orderSpecifierList = new ArrayList<>();

        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    // 기본 정렬조건: 추천순
                    case "bookmark": // 추천순
                        orderSpecifierList.add(QueryDslUtil.getSortedColumn(direction, store, "bookmarkCount"));
                        break;
                    case "review": // 후기순
                        orderSpecifierList.add(QueryDslUtil.getSortedColumn(direction, store, "reviewCount"));
                        break;
                    case "rate": // 평점순
                        orderSpecifierList.add(QueryDslUtil.getSortedColumn(direction, store, "avgRate"));
                        break;
                    case "id": // 최신순
                        orderSpecifierList.add(QueryDslUtil.getSortedColumn(direction, store, "id"));
                        break;
                    default:
                        break;
                }
            }
        }

        return orderSpecifierList;
    }
}
