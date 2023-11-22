package com.kusitms.jipbap.review.model.entity;

import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.order.model.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_review")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id; //고유 pk

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long rating;
    private String message;
    private String image;

}