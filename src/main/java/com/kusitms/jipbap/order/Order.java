package com.kusitms.jipbap.order;

import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.food.Food;
import com.kusitms.jipbap.user.User;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_order")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id; //고유 pk

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @NotNull
    private Long orderCount;

    @NotNull
    private Long totalPrice;

    @NotNull
    private Long regionId; // 관계로 묶으면 너무 많아질 것 같아서 일단 Long으로

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}