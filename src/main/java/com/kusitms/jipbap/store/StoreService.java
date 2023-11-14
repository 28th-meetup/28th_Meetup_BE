package com.kusitms.jipbap.store;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kusitms.jipbap.common.exception.S3RegisterFailureException;
import com.kusitms.jipbap.common.utils.S3Util;
import com.kusitms.jipbap.store.dto.BookmarkedStoreListResponseDto;
import com.kusitms.jipbap.store.dto.RegisterStoreRequestDto;
import com.kusitms.jipbap.store.dto.StoreDetailResponseDto;
import com.kusitms.jipbap.store.dto.StoreDto;
import com.kusitms.jipbap.store.exception.StoreExistsException;
import com.kusitms.jipbap.store.exception.StoreNotExistsException;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.UserRepository;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreBookmarkRepository storeBookmarkRepository;
    private final UserRepository userRepository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public StoreDto registerStore(String email, RegisterStoreRequestDto dto, MultipartFile image) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));
        String imageUri = null;
        if(storeRepository.existsByOwner(user)) {
            throw new StoreExistsException("이미 가게를 생성한 유저입니다.");
        }

        //이미지가 null이 아닌 경우 s3 업로드
        if(image!=null) {
            try {
                imageUri = S3Util.saveFile(amazonS3, bucket, image);
            } catch (IOException e) {
                throw new S3RegisterFailureException("가게 이미지 저장 중 오류가 발생했습니다.");
            }
        }

        Store store = storeRepository.save(
                new Store(null, user, dto.getName(), dto.getDescription(), dto.getKoreanYn(), 0D, dto.getMinOrderAmount(), imageUri, 0L, 0L,  0L)
        );

        return new StoreDto(store.getId(), store.getName(), store.getDescription(), store.getKoreanYn(), store.getAvgRate(), store.getBookmarkCount(), store.getImage());
    }

    @Transactional
    public Slice<StoreDetailResponseDto> searchStoreList(String email, Pageable pageable, String keyword, String standard, String order, Long lastId) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));

        return storeRepository.searchByKeywordOrderBySort(user, pageable, keyword, standard, order, lastId);
    }

    @Transactional
    public StoreDetailResponseDto getStoreDetail(String email, Long storeId) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));

        Store store = storeRepository.findById(storeId).orElseThrow(()-> new StoreNotExistsException("storeId: "+storeId+"에 해당하는 가게가 존재하지 않습니다."));
        return new StoreDetailResponseDto(
                new StoreDto(
                    store.getId(),
                    store.getName(),
                    store.getDescription(),
                    store.getKoreanYn(),
                    store.getAvgRate(),
                    store.getMinOrderAmount(),
                    store.getImage()
                ),
                isStoreBookmarked(user, store)
        );
    }

    @Transactional
    public StoreDto bookmarkStore(String email, Long storeId) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));
        Store store = storeRepository.findById(storeId).orElseThrow(()-> new StoreNotExistsException("storeId: "+storeId+"에 해당하는 가게가 존재하지 않습니다."));

        storeBookmarkRepository.save(new StoreBookmark(null, user, store));
        store.increaseBookmarkCount();

        return new StoreDto(
                null,
                store.getName(),
                store.getDescription(),
                store.getKoreanYn(),
                store.getAvgRate(),
                store.getMinOrderAmount(),
                store.getImage()
                );
    }

    @Transactional
    public BookmarkedStoreListResponseDto findBookmarkedStore(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));

        List<StoreBookmark> storeBookmarks = storeBookmarkRepository.findByUser(user);
        List<StoreDto> sbList = new ArrayList<>();

        for (StoreBookmark sb : storeBookmarks) {
            sbList.add(new StoreDto(
                    sb.getId(),
                    sb.getStore().getName(),
                    sb.getStore().getDescription(),
                    sb.getStore().getKoreanYn(),
                    sb.getStore().getAvgRate(),
                    sb.getStore().getMinOrderAmount(),
                    sb.getStore().getImage()
                )
            );
        }

        return new BookmarkedStoreListResponseDto(sbList);
    }

    private Boolean isStoreBookmarked(User user, Store store){
        return storeBookmarkRepository.existsByUserAndStore(user, store);
    }

}
