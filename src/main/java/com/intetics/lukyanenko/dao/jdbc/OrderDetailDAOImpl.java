package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.OrderDetailDAO;
import com.intetics.lukyanenko.models.Order;
import com.intetics.lukyanenko.models.OrderDetail;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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
    params.put("price", model.getItemPrice());
    jdbcTemplate.update("insert " +
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
                        "  )",
                        params
    );
    model.setId(id);
  }
  
  public void update(OrderDetail model)
  {
    HashMap<String, Object> params = new HashMap<>();
    params.put("id", model.getId());
    params.put("quantity", model.getQuantity());
    jdbcTemplate.update("update order_detail " +
                        "  set quantity = :quantity " +
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
  
  @Override
  protected OrderDetail mapFields(ResultSet resultSet, String fieldNamePrefix)
          throws SQLException
  {
    OrderDetail object = new OrderDetail();
    object.setId(resultSet.getInt(fieldNamePrefix + "id"));
    object.setGoodsItemId(resultSet.getInt(fieldNamePrefix + "goods_item_id"));
    object.setOrderId(resultSet.getInt(fieldNamePrefix + "order_head_id"));
    object.setQuantity(resultSet.getDouble(fieldNamePrefix + "quantity"));
    object.setItemPrice(resultSet.getDouble(fieldNamePrefix + "price"));
    return object;
  }
  
  @Override
  public OrderDetail findGoodsItemInOrder(Order order, Integer goodsItemId)
  {
    HashMap<String, Object> params = new HashMap<>(2);
    params.put("order_id", order.getId());
    params.put("goods_item_id", goodsItemId);
    return jdbcTemplate.query(
      String.format("%s where order_head_id = :order_id and goods_item_id = :goods_item_id", getBaseSelectSQL()),
      params,
      new ResultSetExtractor<OrderDetail>()
      {
        public OrderDetail extractData(ResultSet resultSet)
          throws SQLException, DataAccessException
        {
          if (resultSet.next())
            return mapFields(resultSet);
          else
            return null;
        }
      }
    );
  }
  
  @Override
  public List<OrderDetail> getListForOrder(Order order, SubdetailsFillable subDetails)
  {
    HashMap<String, Object> params = new HashMap<>(1);
    params.put("order_id", order.getId());
    return jdbcTemplate.query(
      "select d.*, " +
      "       g.id item_id," +
      "       g.name item_name," +
      "       g.description item_description," +
      "       g.price item_price" +
      "  from order_detail d" +
      "       inner join goods_item g" +
      "         on d.goods_item_id = g.id" +
      " where order_head_id = :order_id",
      params,
      new RowMapper<OrderDetail>()
      {
        public OrderDetail mapRow(ResultSet resultSet, int i)
          throws SQLException
        {
          OrderDetail detail = mapFields(resultSet);
          if (subDetails != null)
            subDetails.fill(detail, resultSet, "item_");
          return detail;
        }
      }
    );
  }
}
