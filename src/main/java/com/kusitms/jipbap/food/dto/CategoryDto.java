package com.kusitms.jipbap.food.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;
    private String image;
}
