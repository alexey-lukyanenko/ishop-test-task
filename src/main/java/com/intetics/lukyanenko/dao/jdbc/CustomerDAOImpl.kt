package com.intetics.lukyanenko.dao.jdbc

import com.intetics.lukyanenko.dao.CustomerDAO
import com.intetics.lukyanenko.models.Customer
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.util.*

@Service
class CustomerDAOImpl : CommonDAOImpl<Customer>(), CustomerDAO {
    private val sqlInsert = """
        insert
          into customer (
            id,
            app_user_name,
            email,
            phone,
            shipping_address,
            anonymous_session_id
          ) values (
            :id,
            :app_user_name,
            :email,
            :phone,
            :shipping_address,
            :anonymous_session_id
          )""".trimIndent()
    private val sqlUpdate = """
        update customer (
          set app_user_name    = :app_user_name,
              email            = :email,
              phone            = :phone,
              shipping_address = :shipping_address,
              anonymous_session_id = :anonymous_session_id
          where id = :id""".trimIndent()
    private val sqlDelete = """
        delete from customer (
          where id = :id
            and not exists (select *
                              from order_head
                              where customer_id = :id
                           )""".trimIndent()

    override fun getBaseSelectSQL() = "select * from customer"

    override fun mapFields(resultSet: ResultSet, fieldNamePrefix: String): Customer {
        val obj = newModelInstance()
        obj.id = resultSet.getInt(fieldNamePrefix + "id")
        //    object.setAnonymousSessionID(resultSet.getString("anonymous_session_id"));
        obj.appUserName = resultSet.getString(fieldNamePrefix + "app_user_name")
        obj.email = resultSet.getString(fieldNamePrefix + "email")
        obj.phone = resultSet.getString(fieldNamePrefix + "phone")
        obj.shippingAddress = resultSet.getString(fieldNamePrefix + "shipping_address")
        return obj
    }

    override fun add(model: Customer) {
        val params = HashMap<String, Any?>()
        val id = generateNewID()
        params.put("id", id)
        params.put("app_user_name", model.appUserName)
        params.put("email", model.email)
        params.put("phone", model.phone)
        params.put("shipping_address", model.shippingAddress)
        params.put("anonymous_session_id", model.anonymousSessionID)
        jdbcTemplate.update(sqlInsert, params)
        model.id = id
    }

    override fun update(model: Customer) {
        val params = HashMap<String, Any?>()
        params.put("id", model.id)
        params.put("app_user_name", model.appUserName)
        params.put("email", model.email)
        params.put("phone", model.phone)
        params.put("shipping_address", model.shippingAddress)
        params.put("anonymous_session_id", model.anonymousSessionID)
        jdbcTemplate.update(sqlUpdate, params)
    }

    override fun delete(model: Customer) {
        jdbcTemplate.update(sqlDelete, mapOf("id" to model.id))
    }

    override fun generateNewID(): Int {
        return jdbcTemplate.queryForObject("select next value for sq_customer",
                mapOf<String, Any>(),
                Int::class.java)
    }

    override fun find(sessionId: String): Customer? {
        return get("anonymous_session_id", sessionId)
    }
}
