package com.satyabhushan.product_service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateProductRequest {
    private String title;
    private String description;
    private double price;
    private String image ;
    private String category;
}
