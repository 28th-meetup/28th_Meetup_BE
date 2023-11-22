package com.kusitms.jipbap.food;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_food_option")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    private String name;

    private Double dollarPrice;
    private Double canadaPrice;

}
