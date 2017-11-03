package com.intetics.lukyanenko.dao

import com.intetics.lukyanenko.models.Customer

interface CustomerDAO : CommonDAO<Customer> {
    fun find(sessionId: String): Customer?
}
