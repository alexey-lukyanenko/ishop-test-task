package com.intetics.lukyanenko.dao.jdbc

import com.intetics.lukyanenko.dao.GoodsItemDAO
import com.intetics.lukyanenko.dao.OrderDetailDAO
import com.intetics.lukyanenko.models.Order
import com.intetics.lukyanenko.models.OrderDetail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Service

import java.sql.ResultSet
import java.util.*

@Service
class OrderDetailDAOImpl : CommonDAOImpl<OrderDetail>(), OrderDetailDAO {

    private val sqlInsert = "insert " +
            "  into order_detail (" +
            "    id," +
            "    order_head_id," +
            "    goods_item_id," +
            "    quantity," +
            "    price" +
            "  ) values (" +
            "    :id," +
            "    :order_head_id," +
            "    :goods_item_id," +
            "    :quantity," +
            "    :price" +
            "  )"

    private val sqlSelectDetails = "select d.*, " +
            "       g.id item_id," +
            "       g.name item_name," +
            "       g.description item_description," +
            "       g.price item_price" +
            "  from order_detail d" +
            "       inner join goods_item g" +
            "         on d.goods_item_id = g.id" +
            " where order_head_id = :order_id"

    private val sqlUpdate = "update order_detail " +
            "  set quantity = :quantity " +
            "  where id = :id"

    private val sqlDelete = "delete from order_detail where id = :id"

    @Autowired
    private lateinit var goodsItemDao: GoodsItemDAO

    override fun add(model: OrderDetail) {
        val params = HashMap<String, Any?>()
        val id = generateNewID()
        params.put("id", id)
        params.put("order_head_id", model.orderId)
        params.put("goods_item_id", model.goodsItemId)
        params.put("quantity", model.quantity)
        params.put("price", model.itemPrice)
        jdbcTemplate.update(sqlInsert, params)
        model.id = id
    }

    override fun update(model: OrderDetail) {
        jdbcTemplate.update(sqlUpdate, mapOf("id" to model.id, "quantity" to model.quantity))
    }

    override fun delete(model: OrderDetail) {
        jdbcTemplate.update(sqlDelete, mapOf("id" to model.id));
    }

    override fun generateNewID(): Int {
        return jdbcTemplate.queryForObject("select next value for sq_order_detail",
                mapOf<String, Any>(),
                Int::class.java)
    }

    override fun getBaseSelectSQL() = "select * from order_detail"

    override fun mapFields(resultSet: ResultSet, fieldNamePrefix: String): OrderDetail {
        return newModelInstance().apply {
            id = resultSet.getInt(fieldNamePrefix + "id")
            goodsItemId = resultSet.getInt(fieldNamePrefix + "goods_item_id")
            orderId = resultSet.getInt(fieldNamePrefix + "order_head_id")
            quantity = resultSet.getDouble(fieldNamePrefix + "quantity")
            itemPrice = resultSet.getDouble(fieldNamePrefix + "price")
        }
    }

    override fun findGoodsItemInOrder(order: Order, goodsItemId: Int): OrderDetail? {
        return jdbcTemplate.query(
                String.format("%s where order_head_id = :order_id and goods_item_id = :goods_item_id", getBaseSelectSQL()),
                mapOf("order_id" to order.id, "goods_item_id" to goodsItemId),
                ResultSetExtractor<OrderDetail> { resultSet -> resultSet.takeIf { it.next() }?.let { mapFields(resultSet) } }
        )
    }

    override fun getListForOrder(order: Order): MutableList<OrderDetail> {
        return jdbcTemplate.query(sqlSelectDetails, mapOf("order_id" to order.id))
        { resultSet, i ->
            mapFields(resultSet).apply {
                goodsItem = goodsItemDao.populateFromResultSet(resultSet, "item_")
            }
        }
    }
}
