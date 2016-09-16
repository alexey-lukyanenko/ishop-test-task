package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.GoodsItemDAO;
import com.intetics.lukyanenko.models.GoodsCategory;
import com.intetics.lukyanenko.models.GoodsItem;
import javafx.util.Pair;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GoodsItemDAOImpl extends CommonDAOImpl<GoodsItem> implements GoodsItemDAO
{
  public void add(GoodsItem model)
  {
    HashMap<String, Object> params = new HashMap<>();
    int                     id     = generateNewID();
    params.put("name", model.getName());
    params.put("description", model.getDescription());
    params.put("price", model.getPrice());
    params.put("id", id);
    jdbcTemplate.update(
      "insert " +
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
      "  )",
      params
    );
    model.setId(id);
    updateCategoryLinks(model.getCategories(), model.getId());
  }
  
  public void update(GoodsItem model)
  {
    HashMap<String, Object> params = new HashMap<>();
    params.put("name", model.getName());
    params.put("description", model.getDescription());
    params.put("price", model.getPrice());
    params.put("id", model.getId());
    jdbcTemplate.update("update goods_item " +
                        "  set name = :name," +
                        "      description = :description," +
                        "      price = :price " +
                        "  where id = :id",
                        params
    );
    updateCategoryLinks(model.getCategories(), model.getId());
  }
  
  private void updateCategoryLinks(List<GoodsCategory> categories, int goodsItemId)
  {
    HashMap<String, Object> params = new HashMap<>(2);
    params.put("goods_item_id", goodsItemId);
    //
    jdbcTemplate.update("delete " +
                        "  from goods_category_link" +
                        "  where goods_item_id = :goods_item_id",
                        params
    );
    params.put("goods_category_id", null);
    for(GoodsCategory category: categories)
    {
      params.replace("goods_category_id", category.getId());
      jdbcTemplate.update("insert " +
                          "  into goods_category_link (" +
                          "    goods_item_id," +
                          "    goods_category_id" +
                          "  ) values (" +
                          "    :goods_item_id," +
                          "    :goods_category_id" +
                          "  )",
                          params
      );
    }
  }
  
  public void delete(GoodsItem model)
  {
    HashMap<String, Object> params = new HashMap<>();
    params.put("id", model.getId());
    jdbcTemplate.update("delete " +
                        "  from goods_category_link" +
                        "  where goods_item_id = :id",
                        params
    );
    jdbcTemplate.update("delete " +
                        "  from goods_item" +
                        "  where id = :id",
                        params
    );
  }
  
  public int generateNewID()
  {
    return jdbcTemplate.queryForObject("select next value for sq_goods_item",
                                       (HashMap<String, Object>)null,
                                       Integer.class);
  }
  
  protected String getBaseSelectSQL()
  {
    return "select * from goods_item";
  }
  
  protected GoodsItem mapFields(ResultSet resultSet, String fieldNamePrefix)
          throws SQLException
  {
    GoodsItem object = getNewModelInstance();
    object.setId(resultSet.getInt(fieldNamePrefix + "id"));
    object.setName(resultSet.getString(fieldNamePrefix + "name"));
    object.setDescription(resultSet.getString(fieldNamePrefix + "description"));
    object.setPrice(resultSet.getDouble(fieldNamePrefix + "price"));
    return object;
  }
  
  @Override
  public List<GoodsItem> selectWhere(ArrayList<Pair<String, Pair<String, Object>>> params)
  {
    HashMap<String, Object> sqlParams = new HashMap<>();
    StringBuilder sql = new StringBuilder(getBaseSelectSQL());
    sql.append("  where 1 = 1");
    for (Pair<String, Pair<String, Object>> param : params) {
      String prm = String.format("param%d", sqlParams.size());
      if (param.getKey().contentEquals("price")) {
        sql.append("    and price ");
        sql.append(param.getValue().getKey().replace("$(param)", ':' + prm));
        sqlParams.put(prm, param.getValue().getValue());
      }
      if (param.getKey().contentEquals("category")) {
        sql.append(
        "    and id in (select link.goods_item_id " +
        "                 from goods_category_link link " +
        "                      inner join goods_category gc" +
        "                        on link.goods_category_id = gc.id" +
        "                 where gc.name ");
        sql.append(param.getValue().getKey().replace("$(param)", ':' + prm));
        sql.append(")");
        sqlParams.put(prm, param.getValue().getValue());
      }
      if (param.getKey().contentEquals("name")) {
        sql.append("    and (name ");
        sql.append(param.getValue().getKey().replace("$(param)", ':' + prm));
        sql.append("   or description ");
        sql.append(param.getValue().getKey().replace("$(param)", ':' + prm));
        sql.append(")");
        sqlParams.put(prm, param.getValue().getValue());
      }
    }
    return jdbcTemplate.query(
      sql.toString(),
      sqlParams,
      new RowMapper<GoodsItem>()
      {
        public GoodsItem mapRow(ResultSet resultSet, int i)
        throws SQLException
        {
          return mapFields(resultSet);
        }
      }
    );
  }
}
