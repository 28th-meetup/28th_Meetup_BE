package com.kusitms.jipbap.store;

import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "tb_store")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="store_id")
    private Long id; //고유 pk

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    //TODO("읍면동 지역 id?")

    @Column(name = "store_name")
    private String name;
    private String description;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean koreanYn; //한국인 인증 여부

    private Double avgRate; // 가게 평점
    private Long rateCount; // 평점 개수
    private Long bookmarkCount; // 가게 즐겨찾기 횟수 (추천순)
    private Long reviewCount; // 가게 후기 개수

    private Long minOrderAmount; //최소 주문 금액

    private String image;

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