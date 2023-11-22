package com.kusitms.jipbap.order.model.entity;

import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.food.model.entity.Food;
import com.kusitms.jipbap.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_cart")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends DateEntity {

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

}