package com.kusitms.jipbap.food;

import com.kusitms.jipbap.common.entity.DateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="category_id")
    private Long id; //고유 pk

    @Column(name = "category_name")
    private String name;
    private String image;

}