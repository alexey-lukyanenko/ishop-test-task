package com.intetics.lukyanenko.models

class OrderDetail : Common() {
    var id: Int = 0
    var goodsItem: GoodsItem? = null
    var order: Order? = null
    var quantity: Double = 0.toDouble()
    var itemPrice: Double = 0.toDouble()
        get() = goodsItem?.price ?: field

    var goodsItemId: Int? = null
        get() = goodsItem?.id ?: field
        set(value) {
            if (value != goodsItemId) order = null
            field = value
        }

    var orderId: Int? = null
        get() = order?.id ?: field
        set(value) {
            if (value != orderId) order = null
            field = value
        }
}
