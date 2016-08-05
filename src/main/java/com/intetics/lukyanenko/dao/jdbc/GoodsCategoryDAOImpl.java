package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.GoodsCategoryDAO;
import com.intetics.lukyanenko.models.GoodsCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class GoodsCategoryDAOImpl extends CommonDAOImpl<GoodsCategory> implements GoodsCategoryDAO
{
  protected String getBaseSelectSQL()
  {
    return "select * from goods_category";
  }
  
  protected GoodsCategory mapFields(ResultSet resultSet)
          throws SQLException
  {
    GoodsCategory object = new GoodsCategory();
    object.setId(resultSet.getInt("id"));
    object.setName(resultSet.getString("name"));
    return object;
  }
  
  public void add(GoodsCategory model)
  {
    HashMap<String, Object> params = new HashMap<>();
    int id = generateNewID();
    params.put("name", model.getName());
    params.put("id", id);
    jdbcTemplate.update(
      "insert " +
      "  into goods_category (" +
      "    id," +
      "    name" +
      "  ) values (" +
      "    :id," +
      "    :name" +
      "  )",
      params
    );
    model.setId(id);
  }
  
  public void update(GoodsCategory model)
  {
    HashMap<String, Object> params = new HashMap<>();
    params.put("name", model.getName());
    params.put("id", model.getId());
    jdbcTemplate.update("update goods_category " +
                        "  set name = :name " +
                        "  where id = :id",
                        params
    );
  }
  
  public void delete(GoodsCategory model)
  {
    HashMap<String, Object> params = new HashMap<>();
    params.put("name", model.getName());
    params.put("id", model.getId());
    jdbcTemplate.update("delete goods_category " +
                        "  where id = :id" +
                        "    and not exists(select 1 " +
                        "                     from goods_category_link" +
                        "                     where goods_category_id = :id)",
                        params
    );
  }
  
  public int generateNewID()
  {
    return jdbcTemplate.queryForObject("select next value for sq_goods_category",
                                       (HashMap<String, Object>)null,
                                       Integer.class);
  }
}
