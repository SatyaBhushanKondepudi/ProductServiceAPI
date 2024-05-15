package com.satyabhushan.product_service.controllers;

import com.satyabhushan.product_service.dtos.ErrorDto;
import com.satyabhushan.product_service.dtos.ProductDeleteResponseDto;
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

    private ProductResponseDto convertToProductResponseDto(Product product) {
        String categoryTitle = product.getCategory().getTitle();
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
        productResponseDto.setCategory(categoryTitle);
        return productResponseDto;
    }

    private ProductDeleteResponseDto convertToProductDeleteResponseDto(Product product) {
        ProductDeleteResponseDto productDeleteResponseDto = modelMapper.map(product, ProductDeleteResponseDto.class);
        return productDeleteResponseDto;
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
            productResponseDtoList.add(convertToProductResponseDto(product));
        }
        return productResponseDtoList;
    }


    //Get a single product
    @GetMapping("/products/{id}")
    public ProductResponseDto getProduct(@PathVariable("id") int productId) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(productId);
        return convertToProductResponseDto(product);
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
        ProductResponseDto response = convertToProductResponseDto(product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    //Update a product

    @PatchMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") int productId,
                                                            @RequestBody ProductRequestDto productRequestDto)
            throws ProductNotFoundException {
        Product product = productService.updateProduct(productId,
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice() );
        ProductResponseDto productResponseDto = convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> replaceProduct(@PathVariable("id") int productId,
                                                             @RequestBody ProductRequestDto productRequestDto)
            throws ProductNotFoundException {
        Product product = productService.replaceProduct(productId,
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice() );
        ProductResponseDto productResponseDto = convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }



    //Delete a product
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductDeleteResponseDto> deleteProduct(@PathVariable("id") int productId)
            throws ProductNotFoundException {
        Product product = productService.deleteProduct(productId);
        ProductDeleteResponseDto productDeleteResponseDto = convertToProductDeleteResponseDto(product);
        productDeleteResponseDto.setRemoval_Response("Successfully deleted product");
        return new ResponseEntity<>(productDeleteResponseDto, HttpStatus.OK);
    }










}
