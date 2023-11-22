package com.kusitms.jipbap.order.model.entity;

import com.kusitms.jipbap.common.entity.DateEntity;
import com.kusitms.jipbap.review.model.entity.Review;
import com.kusitms.jipbap.store.model.entity.Store;
import com.kusitms.jipbap.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

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
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull
    private Double totalPrice; //총 주문 결제 금액
    private Long totalCount; //총 주문한 메뉴 개수

    @NotNull
    private Long regionId; // 관계로 묶으면 너무 많아질 것 같아서 일단 Long으로

    @NotNull
    @Column(name = "selected_option")
    private String selectedOption; //선택한 옵션(배달, 픽업)

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetail;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Review review;
}