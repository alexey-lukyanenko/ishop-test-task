package com.intetics.lukyanenko.dao

import com.intetics.lukyanenko.models.Order

interface OrderDAO : CommonDAO<Order> {
    fun findBasket(customerId: Int): Order?

    fun convertBasketToOrder(basket: Order): Order
}
