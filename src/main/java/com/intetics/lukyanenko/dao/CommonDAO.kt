package com.intetics.lukyanenko.dao

import java.sql.ResultSet
import java.sql.SQLException

interface CommonDAO<T> {
    fun findAll(): List<T>
    fun add(model: T)
    fun update(model: T)
    fun delete(model: T)
    fun generateNewID(): Int

    fun getByID(id: Int): T?

    @Throws(SQLException::class)
    fun populateFromResultSet(resultSet: ResultSet, fieldNamePrefix: String): T
}
