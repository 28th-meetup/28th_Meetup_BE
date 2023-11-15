package com.kusitms.jipbap.store;

import com.kusitms.jipbap.chat.domain.entity.Message;
import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.food.Food;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.entity.GlobalRegion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "store_name")
    private String name;
    private String description;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean koreanYn; //한국인 인증 여부

    private Double avgRate; // 가게 평점
    private Long minOrderAmount; //최소 주문 금액
    private String image;

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
        this.avgRate = (avgRate*rateCount+newRate)/(rateCount+1);
    }
}