package com.satyabhushan.product_service.services;

import com.satyabhushan.product_service.dtos.ProductResponseDto;
import com.satyabhushan.product_service.exceptions.ProductNotFoundException;
import com.satyabhushan.product_service.models.Category;
import com.satyabhushan.product_service.models.Product;

import java.util.List;

public interface ProductService {

    // Get all products
    public List<Product> getAllProducts();

    //Get a single product
    public Product getSingleProduct(int id) throws ProductNotFoundException;

    //Get all categories
    public List<Category> getAllCategories();

    //Get products in a specific category
    public  List<Product> getSpecificCategoryProducts(String category);

    //Add new product
    public Product addProduct(
            String title,
            String description,
            String imageUrl ,
            String category ,
            double price
    );

    //Update a product

    //Delete a product

}
