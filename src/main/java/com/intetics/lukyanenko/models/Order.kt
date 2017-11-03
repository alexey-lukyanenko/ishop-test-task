package com.intetics.lukyanenko.models

import java.util.*

class Order : Common() {
    var id: Int = 0
    var number: String? = null
    lateinit var created: Date
    var isBasket: Boolean = false
    var customer: Customer? = null
        get() =
            field ?: customerId?.let {
                field = daoFactory.getDAO(Customer::class).getByID(it)
                field
            }
        set(value) {
            field = value
            customerId = value?.id
        }

    var customerId: Int? = null
        set(value) {
            if (customer?.id != value)
                customer = null
            field = value
        }

    var details: MutableList<OrderDetail>? = null

    fun getTotal(): Double? = details?.sumByDouble { it.quantity * it.itemPrice } // преимущество Kotlin от Java стоит показать на этом примере
}