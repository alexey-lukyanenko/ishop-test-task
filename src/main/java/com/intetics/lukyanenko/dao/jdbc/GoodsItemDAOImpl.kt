package com.intetics.lukyanenko.dao.jdbc

import com.intetics.lukyanenko.dao.GoodsItemDAO
import com.intetics.lukyanenko.models.GoodsCategory
import com.intetics.lukyanenko.models.GoodsItem
import javafx.util.Pair
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.util.*

@Service
class GoodsItemDAOImpl : CommonDAOImpl<GoodsItem>(), GoodsItemDAO {
    private val sqlInsert = "insert " +
            "  into goods_item (" +
            "    id," +
            "    name," +
            "    description," +
            "    price" +
            "  ) values (" +
            "    :id," +
            "    :name," +
            "    :description," +
            "    :price" +
            "  )"
    private val paramId = "goods_item_id"
    private val sqlUpdate = "update goods_item " +
            "  set name = :name," +
            "      description = :description," +
            "      price = :price " +
            "  where id = :id"

    private val sqlDeleteCategoryLinks = ("delete " +
            "  from goods_category_link" +
            "  where goods_item_id = :" + paramId).trimIndent()

    private val sqlContainsCategory = """
                and id in (select link.goods_item_id
                             from goods_category_link link
                                  inner join goods_category gc
                                    on link.goods_category_id = gc.id
                             where gc.name """.trimIndent()

    private val sqlInsertCategoryLink = "insert " +
            "  into goods_category_link (" +
            "    goods_item_id," +
            "    goods_category_id" +
            "  ) values (" +
            "    :" + paramId + "," +
            "    :goods_category_id" +
            "  )"

    private val sqlDelete = "delete " +
            "  from goods_item" +
            "  where id = :id"

    override fun add(model: GoodsItem) {
        val params = HashMap<String, Any>()
        val id = generateNewID()
        params.put("name", model.name)
        params.put("description", model.description)
        params.put("price", model.price)
        params.put("id", id)
        jdbcTemplate.update(sqlInsert, params)
        model.id = id
        updateCategoryLinks(model.categories, id)
    }

    override fun update(model: GoodsItem) {
        val params = HashMap<String, Any?>()
        params.put("name", model.name)
        params.put("description", model.description)
        params.put("price", model.price)
        params.put("id", model.id)
        jdbcTemplate.update(sqlUpdate, params)
        updateCategoryLinks(model.categories, model.id!!)
    }

    private fun updateCategoryLinks(categories: Collection<GoodsCategory>, goodsItemId: Int) {
        jdbcTemplate.update(sqlDeleteCategoryLinks, mapOf("goods_item_id" to goodsItemId))
        for (category in categories) {
            jdbcTemplate.update(
                    sqlInsertCategoryLink,
                    mapOf("goods_item_id" to goodsItemId, "goods_category_id" to category.id))
        }
    }

    override fun delete(model: GoodsItem) {
        jdbcTemplate.update(sqlDeleteCategoryLinks, mapOf(paramId to model.id))
        jdbcTemplate.update(sqlDelete, mapOf(paramId to model.id))
    }

    override fun generateNewID(): Int {
        return jdbcTemplate.queryForObject("select next value for sq_goods_item",
                Collections.emptyMap<String, Any>(),
                Int::class.java)
    }

    override fun getBaseSelectSQL() = "select * from goods_item"

    override fun mapFields(resultSet: ResultSet, fieldNamePrefix: String): GoodsItem {
        val obj = newModelInstance()
        obj.id = resultSet.getInt(fieldNamePrefix + "id")
        obj.name = resultSet.getString(fieldNamePrefix + "name")
        obj.description = resultSet.getString(fieldNamePrefix + "description")
        obj.price = resultSet.getDouble(fieldNamePrefix + "price")
        return obj
    }

    override fun selectWhere(params: ArrayList<Pair<String, Pair<String, Any>>>): List<GoodsItem> {
        fun getClauseFromTemplate(template: String, filler: String): String = template.replace("$(param)", filler)
        val sqlParams = HashMap<String, Any>()
        val sql = StringBuilder(getBaseSelectSQL())
        sql.append("  where 1 = 1")
        for (param in params) {
            val parameterName = String.format("param%d", sqlParams.size)
            when (param.key) {
                "price" -> {
                    sql.append("    and price ")
                    sql.append(getClauseFromTemplate(param.value.key, parameterName))
                    sqlParams.put(parameterName, param.value.value)
                }
                "category" -> {
                    sql.append(sqlContainsCategory)
                    sql.append(getClauseFromTemplate(param.value.key, parameterName))
                    sql.append(")")
                    sqlParams.put(parameterName, param.value.value)
                }
                "name" -> {
                    sql.append("    and (name ")
                    sql.append(getClauseFromTemplate(param.value.key, parameterName))
                    sql.append("   or description ")
                    sql.append(getClauseFromTemplate(param.value.key, parameterName))
                    sql.append(")")
                    sqlParams.put(parameterName, param.value.value)
                }
            }
        }
        return jdbcTemplate.query(sql.toString(), sqlParams) { resultSet, i -> mapFields(resultSet) }
    }
}
