package com.satyabhushan.product_service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDeleteResponseDto {
    private int id;
    private String title;
    private String description;
    private String removal_Response = null;
}
