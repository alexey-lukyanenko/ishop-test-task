package com.intetics.lukyanenko.dao.jdbc

import com.intetics.lukyanenko.dao.OrderDAO
import com.intetics.lukyanenko.models.Order
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Service

import java.sql.ResultSet

@Service
class OrderDAOImpl : CommonDAOImpl<Order>(), OrderDAO {
    private val sqlInsert = """
        insert
          into order_head (
            id,
            order_number,
            created,
            customer_id,
            is_basket
          ) values (
            :id,
            :order_number,
            :created,
            :customer_id,
            :is_basket
          )""".trimIndent()
    private val sqlUpdate = """
        update order_head
          set order_number = :order_number,
              customer_id  = :customer_id
          where id = :id
        """.trimIndent()
    private val sqlDeleteDetails = "delete from order_detail where order_head_id = :id"

    private val sqlDelete = "delete from order_head where id = :id"

    override fun getBaseSelectSQL() = "select * from order_head"

    override fun mapFields(resultSet: ResultSet, fieldNamePrefix: String): Order {
        val Order = newModelInstance()
        Order.id = resultSet.getInt(fieldNamePrefix + "id")
        Order.created = resultSet.getDate(fieldNamePrefix + "created")
        Order.isBasket = resultSet.getInt(fieldNamePrefix + "is_basket") != 0
        Order.number = resultSet.getString(fieldNamePrefix + "order_number")
        Order.customerId = resultSet.getInt(fieldNamePrefix + "customer_id")
        return Order
    }

    override fun add(model: Order) {
        val id = generateNewID()
        jdbcTemplate.update(sqlInsert,
                mapOf("id" to id,
                        "order_number" to model.number,
                        "created" to model.created,
                        "customer_id" to model.customerId,
                        "is_basket" to if (model.isBasket) 1 else 0)
        )
        model.id = id
    }

    override fun update(model: Order) {
        jdbcTemplate.update(sqlUpdate,
                mapOf("id" to model.id, "order_number" to model.number, "customer_id" to model.customerId)
        )
    }

    override fun delete(model: Order) {
        val params = mapOf("id" to model.id)
        jdbcTemplate.update(sqlDeleteDetails, params)
        jdbcTemplate.update(sqlDelete, params)
    }

    override fun generateNewID(): Int {
        return jdbcTemplate.queryForObject("select next value for sq_order_head",
                mapOf<String, Any>(),
                Int::class.java)
    }

    override fun findBasket(customerId: Int): Order? {
        return jdbcTemplate.query(
                String.format("%s where is_basket = 1 and customer_id = :customer_id", getBaseSelectSQL()),
                mapOf("customer_id" to customerId),
                ResultSetExtractor<Order> { resultSet -> if (resultSet.next()) mapFields(resultSet) else null }
        )
    }

    override fun convertBasketToOrder(basket: Order): Order {
        jdbcTemplate.update("update order_head set is_basket = 0 where id = :id", mapOf("id" to basket.id))
        basket.isBasket = false
        return basket
    }
}