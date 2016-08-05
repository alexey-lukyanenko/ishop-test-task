package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.GoodsItemDAO;
import com.intetics.lukyanenko.models.GoodsCategory;
import com.intetics.lukyanenko.models.GoodsItem;

import java.sql.ResultSet;
import java.sql.SQLException;
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
  
  protected GoodsItem mapFields(ResultSet resultSet)
          throws SQLException
  {
    GoodsItem object = new GoodsItem();
    object.setId(resultSet.getInt("id"));
    object.setName(resultSet.getString("name"));
    object.setDescription(resultSet.getString("description"));
    object.setPrice(resultSet.getDouble("price"));
    return object;
  }
}
