package com.kusitms.jipbap.food;

import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.store.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "tb_food")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Food extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="food_id")
    private Long id; //고유 pk

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Category category;

    private String name;
    private Long price;
    private String description;
    private Long recommendCount;

}