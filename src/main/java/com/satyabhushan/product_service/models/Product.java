package com.satyabhushan.product_service.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel{
//    private int id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Category category;
}
