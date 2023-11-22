package com.kusitms.jipbap.store;

import com.kusitms.jipbap.chat.domain.entity.Message;
import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.food.Food;
import com.kusitms.jipbap.user.CountryPhoneCode;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.entity.GlobalRegion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_store")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id; //고유 pk

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "global_region_id")
    private GlobalRegion globalRegion; //지역
    private String address;
    private String detailAddress;

    @Column(name = "store_name")
    private String name; //가게이름
    private String description; //가게소개

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)", name="korean_yn")
    private Boolean koreanYn; //한국인 인증 여부

    private Double minOrderAmount; //최소 주문 금액
    private String image;
    private String image2;
    private String image3;

    private CountryPhoneCode countryPhoneCode;
    private String phoneNum;

    @Column(name="devlivery_region")
    private String deliveryRegion;

    @Column(name="operation_time")
    private String operationTime;

    @Column(name="food_change_yn")
    @NotNull
    private Boolean foodChangeYn;

    private Double avgRate; // 가게 평점
    private Long reviewCount; // 가게 후기 개수
    private Long bookmarkCount; // 가게 즐겨찾기 횟수 (추천순)
    private Long rateCount; // 평점 남긴 인원수


    public void increaseBookmarkCount() {
        this.bookmarkCount++;
    }

    public void increaseReviewCount() {
        this.reviewCount++;
    }

    public void updateAvgRate(Double newRate) {
        this.avgRate = roundToTwoDecimals((avgRate*rateCount+newRate)/(++rateCount));
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100) / 100.0;
    }
}