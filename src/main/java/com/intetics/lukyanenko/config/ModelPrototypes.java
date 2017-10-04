package com.intetics.lukyanenko.config;

import com.intetics.lukyanenko.models.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ModelPrototypes {
    
    @Bean
    @Scope("prototype")
    AppUser getAppUser() {
        return new AppUser();
    }
    
    @Bean
    @Scope("prototype")
    Customer getCustomer() {
        return new Customer();
    }
    
    @Bean
    @Scope("prototype")
    GoodsCategory getGoodsCategory() {
        return new GoodsCategory();
    }
    
    @Bean
    @Scope("prototype")
    GoodsItem getGoodsItem() {
        return new GoodsItem();
    }
    
    @Bean
    @Scope("prototype")
    Order getOrder() {
        return new Order();
    }
    
    @Bean
    @Scope("prototype")
    OrderDetail getOrderDetail() {
        return new OrderDetail();
    }
}
