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
    @JoinColumn
    private User owner;

    //읍면동 지역 ID

    private String name;
    private String description;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private Boolean koreanYn;

    private Double avgRate;

    private String image;
}