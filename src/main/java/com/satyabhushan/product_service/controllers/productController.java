package com.satyabhushan.product_service.controllers;

import com.satyabhushan.product_service.dtos.ErrorDto;
import com.satyabhushan.product_service.dtos.ProductRequestDto;
import com.satyabhushan.product_service.dtos.ProductResponseDto;
import com.satyabhushan.product_service.exceptions.ProductNotFoundException;
import com.satyabhushan.product_service.models.Category;
import com.satyabhushan.product_service.models.Product;
import com.satyabhushan.product_service.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class productController {
    private ProductService productService;
    private ModelMapper modelMapper;

    public productController(ProductService productService , ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    private ProductResponseDto convetToProductResponseDto(Product product) {
        String categoryTitle = product.getCategory().getTitle();
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
        productResponseDto.setCategory(categoryTitle);
        return productResponseDto;
    }

    //Add Exception Handler
//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException productNotFoundException){
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setMessage(productNotFoundException.getMessage());
//        return new ResponseEntity<>(errorDto , HttpStatus.NOT_FOUND);
//    }

    //Get all products
    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() {
        List<Product> productList =  productService.getAllProducts();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Product product : productList) {
            productResponseDtoList.add(convetToProductResponseDto(product));
        }
        return productResponseDtoList;
    }


    //Get a single product
    @GetMapping("/products/{id}")
    public ProductResponseDto getProduct(@PathVariable("id") int productId) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(productId);
        return convetToProductResponseDto(product);
    }

    //Get all categories
    @GetMapping("/products/categories")
    public List<Category> categories() {
        return productService.getAllCategories();
    }

    //Get products in a specific category
    @GetMapping("/products/category/{category_Name}")
    public List<Product> category(@PathVariable("category_Name") String categoryName) {
        return  productService.getSpecificCategoryProducts(categoryName);
    }


    //Add new product
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        Product product =  productService.addProduct(
                productRequestDto.getTitle() , productRequestDto.getDescription(),
                productRequestDto.getImage() , productRequestDto.getCategory() , productRequestDto.getPrice());
//       return modelMapper.map(product, ProductResponseDto.class);
        ProductResponseDto response = convetToProductResponseDto(product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    //Update a product


    //Delete a product








}
