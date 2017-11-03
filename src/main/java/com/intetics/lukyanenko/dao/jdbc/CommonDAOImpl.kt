package com.intetics.lukyanenko.dao.jdbc

import com.intetics.lukyanenko.dao.CommonDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

import javax.inject.Provider
import java.sql.ResultSet
import java.util.HashMap
abstract class CommonDAOImpl<T> : CommonDAO<T> {

    internal lateinit var modelProvider: Provider<T>
        @Autowired set

    internal lateinit var jdbcTemplate: NamedParameterJdbcTemplate
        @Autowired set

    fun newModelInstance(): T = modelProvider.get()

    protected abstract fun getBaseSelectSQL(): String

    override fun findAll(): List<T> {
        return jdbcTemplate.query(getBaseSelectSQL()) { resultSet, i -> mapFields(resultSet) }
    }

    protected abstract fun mapFields(resultSet: ResultSet, fieldNamePrefix: String): T

    protected fun mapFields(resultSet: ResultSet): T = mapFields(resultSet, "")

    override fun getByID(id: Int): T? = get("id", id)

    override fun populateFromResultSet(resultSet: ResultSet, fieldNamePrefix: String): T =
            mapFields(resultSet, fieldNamePrefix)

    protected operator fun get(paramName: String, paramValue: Any): T? {
        return jdbcTemplate.query(
                String.format("%s where %s = :value", getBaseSelectSQL(), paramName),
                mapOf("value" to  paramValue),
                ResultSetExtractor<T> { resultSet ->
                    if (resultSet.next())
                        mapFields(resultSet)
                    else
                        null
                }
        )
    }
}

