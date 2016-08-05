package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.OrderDAO;
import com.intetics.lukyanenko.models.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class OrderDAOImpl extends CommonDAOImpl<Order> implements OrderDAO
{
  @Override
  protected String getBaseSelectSQL()
  {
    return "select * from order_head";
  }
  
  @Override
  protected Order mapFields(ResultSet resultSet)
          throws SQLException
  {
    Order Order = new Order();
    Order.setId(resultSet.getInt("id"));
    Order.setCreated(resultSet.getDate("created"));
    Order.setIsBasket(resultSet.getInt("is_basket") == 0);
    Order.setNumber(resultSet.getString("order_number"));
//                CommonDAO.setTotal(resultSet.getDouble("total"));
//                CommonDAO.setDetails();
//                CommonDAO.setCustomer();
    return Order;
  }
  
  public void add(final Order order)
  {
    final int id = generateNewID();
    jdbcTemplate.update("insert " +
                        "  into order_head (" +
                        "    id, " +
                        "    order_number, " +
                        "    created, " +
                        "    customer_id, " +
                        "    is_basket " +
                        "  ) values (" +
                        "    :id, " +
                        "    :order_number, " +
                        "    :created, " +
                        "    :customer_id, " +
                        "    :is_basket" +
                        "  )",
                        new HashMap<String, Object>(){{
                          put("id", id);
                          put("order_number", order.getNumber());
                          put("created", order.getCreated());
                          put("customer_id", order.getCustomerId());
                          put("is_basket", order.getIsBasket());
                        }}
                       );
    order.setId(id);
  }
  
  public void update(final Order order)
  {
    jdbcTemplate.update("update order_head " +
                        "  set order_number = :order_number, " +
                        "      customer_id  = :customer_id " +
                        "  where id = :id",
                        new HashMap<String, Object>(){{
                          put("id", order.getId());
                          put("order_number", order.getNumber());
                          put("customer_id", order.getCustomerId());
                        }}
    );
  }
  
  public void delete(final Order order)
  {
    HashMap<String, Object> params = new HashMap<>();
    params.put("id", order.getId());
    jdbcTemplate.update("delete from order_detail where order_head_id = :id", params);
    jdbcTemplate.update("delete from order_head where id = :id", params);
  }
  
  public int generateNewID()
  {
    return jdbcTemplate.queryForObject("select next value for sq_order_head",
                                       (HashMap<String, Object>)null,
                                       Integer.class);
  }
}
