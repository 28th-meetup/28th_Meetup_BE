package com.kusitms.jipbap.store;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.food.dto.FoodDetailByStoreResponse;
import com.kusitms.jipbap.order.OrderService;
import com.kusitms.jipbap.order.dto.OrderDto;
import com.kusitms.jipbap.order.dto.OrderPreviewResponse;
import com.kusitms.jipbap.order.dto.OwnerOrderStatusResponse;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import com.kusitms.jipbap.store.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final int PAGESIZE = 3;
    private final int LARGE_PAGESIZE = 10000;
    private final StoreService storeService;
    private final OrderService orderService;

    @Operation(summary = "가게 등록하기")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<RegisterStoreResponse> registerStore(
            @Auth AuthInfo authInfo,
            @RequestPart(value = "dto") RegisterStoreRequestDto dto,
            @RequestPart(value = "image", required = false) List<MultipartFile> image
    ){
        return new CommonResponse<>(storeService.registerStore(authInfo.getEmail(), dto, image));
    }

    /**
     * 가게 검색 api - 페이지네이션 적용
     * api의 복잡도와 성능을 희생하는 대신, 데이터 중복/삭제 현상을 감안함
     *
     * @param keyword: 검색 키워드 (키워드가 포함한 가게를 검색한다)
     * @param field: 검색 기준 (추천-bookmark, 후기-review, 평점-rate, 가격-price, 최신-id)
     *                 (추천순: 가게 즐겨찾기 개수 순서, 후기: 가게에 속한 주문에 달린 리뷰 수)
     * @param direction: 정렬 기준 (ASC, DESC)
     * @param lastId: 결과 리스트에서 마지막으로 출력된 가게의 id
     * @return Slice<?>: 슬라이스 단위
     */
    @Operation(summary = "가게 리스트 검색")
    @GetMapping("/pagenation-deprecated")
    public CommonResponse<Slice<StoreDetailResponseDto>> searchStore(
            @Auth AuthInfo authInfo,
            @RequestParam(required = false) String keyword,
            @RequestParam String field,
            @RequestParam String direction,
            @RequestParam(required = false) Long lastId
    ) {
        Sort sort;
        if ("asc".equals(direction) || "ASC".equals(direction)) {
            sort = Sort.by(Sort.Direction.ASC, field);
        } else {
            sort = Sort.by(Sort.Direction.DESC, field);
        }
        Pageable pageable = PageRequest.of(0, PAGESIZE, sort);
        return new CommonResponse<>(storeService.searchStoreList(authInfo.getEmail(), pageable, keyword, field, direction, lastId));
    }

    /**
     * 가게 및 음식 검색 api - 페이지네이션 미적용
     *
     * @param keyword: 검색 키워드 (키워드가 포함한 가게를 검색한다)
     * @param field: 검색 기준 (추천-bookmark, 후기-review, 평점-rate, 가격-price, 최신-id)
     *                 (추천순: 가게 즐겨찾기 개수 순서, 후기: 가게에 속한 주문에 달린 리뷰 수)
     * @param direction: 정렬 기준 (ASC, DESC)
     */
    @Operation(summary = "가게 또는 메뉴 검색 (페이지네이션 미적용)")
    @GetMapping
    public CommonResponse<StoreFoodResponseDto> searchStoreAndFood(
            @Auth AuthInfo authInfo,
            @RequestParam(required = false) String keyword,
            @RequestParam String field,
            @RequestParam String direction
    ) {
        Sort sort;
        if ("asc".equals(direction) || "ASC".equals(direction)) {
            sort = Sort.by(Sort.Direction.ASC, field);
        } else {
            sort = Sort.by(Sort.Direction.DESC, field);
        }
        Pageable pageable = PageRequest.of(0, LARGE_PAGESIZE, sort);
        return new CommonResponse<>(storeService.searchStoresAndFoods(authInfo.getEmail(), pageable, keyword, field, direction));
    }

    @Operation(summary = "가게 상세정보")
    @GetMapping("/{storeId}")
    public CommonResponse<StoreDetailResponseDto> storeDetail(@Auth AuthInfo authInfo, @PathVariable Long storeId) {
        return new CommonResponse<>(storeService.getStoreDetail(authInfo.getEmail(), storeId));
    }

    @Operation(summary = "가게 찜하기")
    @PostMapping("/bookmark/{storeId}")
    public CommonResponse<StoreDto> bookmarkStore(@Auth AuthInfo authInfo, @PathVariable Long storeId) {
        return new CommonResponse<>(storeService.bookmarkStore(authInfo.getEmail(), storeId));
    }

    @Operation(summary = "찜한 가게 리스트")
    @GetMapping("/bookmark")
    public CommonResponse<BookmarkedStoreListResponseDto> getBookmarkedStoreList(@Auth AuthInfo authInfo) {
        return new CommonResponse<>(storeService.findBookmarkedStore(authInfo.getEmail()));
    }

    @Operation(summary = "특정 가게의 메뉴 모두 가져오기")
    @GetMapping("/{storeId}/menu")
    public CommonResponse<List<FoodDetailByStoreResponse>> getAllMenuListByStoreId(@PathVariable Long storeId) {
        return new CommonResponse<>(storeService.getAllMenuListByStoreId(storeId));
    }

    @Operation(summary = "가게의 주문내역에 맞는 주문 리스트 가져오기")
    @GetMapping("order-history/{orderStatus}")
    public CommonResponse<OwnerOrderStatusResponse> getStoreOrderHistoryByOrderStatus(
            @Auth AuthInfo authInfo,
            @PathVariable String orderStatus) {
        return new CommonResponse<>(orderService.getStoreOrderHistoryByOrderStatus(authInfo.getEmail(), orderStatus));
    }
}
