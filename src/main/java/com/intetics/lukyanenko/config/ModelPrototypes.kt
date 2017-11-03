package com.intetics.lukyanenko.config

import com.intetics.lukyanenko.models.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
open class ModelPrototypes {

    @Bean
    @Scope("prototype")
    open fun appUser(): AppUser = AppUser()

    @Bean
    @Scope("prototype")
    open fun customer(): Customer = Customer()

    @Bean
    @Scope("prototype")
    open fun goodsCategory(): GoodsCategory = GoodsCategory()

    @Bean
    @Scope("prototype")
    open fun goodsItem(): GoodsItem = GoodsItem()

    @Bean
    @Scope("prototype")
    open fun order(): Order = Order()

    @Bean
    @Scope("prototype")
    open fun orderDetail(): OrderDetail = OrderDetail()
}
