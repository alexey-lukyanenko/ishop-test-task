package com.intetics.lukyanenko.dao.jdbc

import com.intetics.lukyanenko.dao.GoodsCategoryDAO
import com.intetics.lukyanenko.models.GoodsCategory
import org.springframework.stereotype.Service

import java.sql.ResultSet

@Service
class GoodsCategoryDAOImpl : CommonDAOImpl<GoodsCategory>(), GoodsCategoryDAO {
    private val sqlInsert = """
        insert
          into goods_category (
            id,
            name
          ) values (
            :id,
            :name
          )""".trimIndent()
    private val sqlUpdate = """
        update goods_category
          set name = :name
          where id = :id""".trimIndent()
    private val sqlDelete = """
        delete goods_category
              where id = :id
                and not exists(select 1
                                 from goods_category_link
                                 where goods_category_id = :id)
        """.trimIndent()
    private val sqlSelect = """
        select gc.*
          from goods_category gc
               inner join goods_category_link link
                 on gc.id = link.goods_category_id
          where link.goods_item_id = :item_id
        """.trimIndent()

    override fun getBaseSelectSQL() = "select * from goods_category"

    override fun mapFields(resultSet: ResultSet, fieldNamePrefix: String): GoodsCategory {
        val obj = newModelInstance()
        obj.id = resultSet.getInt(fieldNamePrefix + "id")
        obj.name = resultSet.getString(fieldNamePrefix + "name")
        return obj
    }

    override fun add(model: GoodsCategory) {
        val id = generateNewID()
        jdbcTemplate.update(sqlInsert, mapOf("id" to id, "name" to model.name))
        model.id = id
    }

    override fun update(model: GoodsCategory) {
        jdbcTemplate.update(sqlUpdate, mapOf("id" to model.id, "name" to model.name))
    }

    override fun delete(model: GoodsCategory) {
        jdbcTemplate.update(sqlDelete, mapOf("id" to model.id))
    }

    override fun generateNewID(): Int {
        return jdbcTemplate.queryForObject("select next value for sq_goods_category",
                mapOf<String, Any>(),
                Int::class.java)
    }

    override fun getGoodsItemCategories(goodsItemId: Int): MutableList<GoodsCategory> {
        return jdbcTemplate.query(sqlSelect, mapOf("item_id" to goodsItemId)) { resultSet, i -> mapFields(resultSet) }
    }
}
