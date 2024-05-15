package com.satyabhushan.product_service.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration   {
    //This will create Bean for a method in FakeStoreProductService
    @Bean
    public RestTemplate createRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ModelMapper createModelMapper(){
        return new ModelMapper();
    }
}
