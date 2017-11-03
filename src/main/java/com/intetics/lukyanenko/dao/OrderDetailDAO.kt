package com.intetics.lukyanenko.dao

import com.intetics.lukyanenko.models.Order
import com.intetics.lukyanenko.models.OrderDetail

interface OrderDetailDAO : CommonDAO<OrderDetail> {
    fun findGoodsItemInOrder(order: Order, goodsItemId: Int): OrderDetail?

    fun getListForOrder(order: Order): MutableList<OrderDetail>
}
