package com.satyabhushan.product_service.services;

import com.satyabhushan.product_service.dtos.FakeStoreDto;
import com.satyabhushan.product_service.dtos.ProductResponseDto;
import com.satyabhushan.product_service.exceptions.ProductNotFoundException;
import com.satyabhushan.product_service.models.Category;
import com.satyabhushan.product_service.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate ;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Get all products
    @Override
    public List<Product> getAllProducts(){
        FakeStoreDto[] fakeStoreDtoArray = restTemplate.getForObject(
                "https://fakestoreapi.com/products" , FakeStoreDto[].class
        );
        // Convert all FakeStoreDto to Product Objects
        List<Product> products = new ArrayList<>();
        for (FakeStoreDto fakeStoreDto : fakeStoreDtoArray) {
            products.add(fakeStoreDto.toProduct());
        }
        return products;
    }

    //Get a single product
    @Override
    public Product getSingleProduct(int productId) throws ProductNotFoundException {
        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId, FakeStoreDto.class
        );
        if(fakeStoreDto == null) {
            throw new ProductNotFoundException(
                    "Product with id : " + productId + " is not found. Try a product with id less than 21"
            );
        }
        return fakeStoreDto.toProduct();
    }

    //Get all categories
    @Override
    public List<Category> getAllCategories(){
        List<Category> categoriesList = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories" , List.class
        );
        return categoriesList;
    }

    //Get products in a specific category
    @Override
    public  List<Product> getSpecificCategoryProducts(String category){
        List<Product> specificCategoryproducts = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + category, List.class
        );
        return specificCategoryproducts;
    }

    //Add new product
    @Override
    public Product addProduct(
            String title,
            String description,
            String imageUrl ,
            String category ,
            double price
    ){
        FakeStoreDto fakeStoreDto = new FakeStoreDto();
        fakeStoreDto.setTitle(title);
        fakeStoreDto.setDescription(description);
        fakeStoreDto.setImage(imageUrl);
        fakeStoreDto.setCategory(category);
        fakeStoreDto.setPrice(price);

        FakeStoreDto response = restTemplate.postForObject(
                "https://fakestoreapi.com/products", fakeStoreDto, FakeStoreDto.class
        );

        return response.toProduct();
    }

    //Update a product

    //Delete a product


}
