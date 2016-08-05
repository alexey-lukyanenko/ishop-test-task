package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.OrderDetailDAO;
import com.intetics.lukyanenko.models.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class OrderDetailDAOImpl
        extends CommonDAOImpl<OrderDetail>
        implements OrderDetailDAO
{
  public void add(OrderDetail model)
  {
    HashMap<String, Object> params = new HashMap<>();
    int id = generateNewID();
    params.put("id", id);
    params.put("order_head_id", model.getOrderId());
    params.put("goods_item_id", model.getGoodsItemId());
    params.put("quantity", model.getQuantity());
    jdbcTemplate.update("insert " +
                        "  into order_detail (" +
                        "    id," +
                        "    order_head_id," +
                        "    goods_item_id," +
                        "    quantity" +
                        "  ) values (" +
                        "    :id," +
                        "    :order_head_id," +
                        "    :goods_item_id," +
                        "    :quantity" +
                        "  )",
                        params
    );
    model.setId(id);
  }
  
  public void update(OrderDetail model)
  {
    HashMap<String, Object> params = new HashMap<>();
    params.put("id", model.getId());
    params.put("goods_item_id", model.getGoodsItemId());
    params.put("quantity", model.getQuantity());
    jdbcTemplate.update("update order_detail " +
                        "  set goods_item_id = :goods_item_id, " +
                        "      quantity      = :quantity " +
                        "  where id = :id",
                        params
    );
  }
  
  public void delete(OrderDetail model)
  {
    HashMap<String, Object> params = new HashMap<>();
    params.put("id", model.getId());
    jdbcTemplate.update("delete from order_detail " +
                        "  where id = :id",
                        params
    );
  }
  
  public int generateNewID()
  {
    return jdbcTemplate.queryForObject("select next value for sq_order_detail",
                                       (HashMap<String, Object>)null,
                                       Integer.class);
  }
  
  protected String getBaseSelectSQL()
  {
    return "select * from order_detail";
  }
  
  protected OrderDetail mapFields(ResultSet resultSet)
          throws SQLException
  {
    OrderDetail object = new OrderDetail();
    object.setId(resultSet.getInt("id"));
    object.setGoodsItemId(resultSet.getInt("goods_item_id"));
    object.setOrderId(resultSet.getInt("order_head_id"));
    object.setQuantity(resultSet.getDouble("quantity"));
    return object;
  }
}
