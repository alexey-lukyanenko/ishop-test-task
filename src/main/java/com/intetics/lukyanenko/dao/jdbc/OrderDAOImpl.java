package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.OrderDAO;
import com.intetics.lukyanenko.models.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAOImpl implements OrderDAO
{
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate)
  {
    this.jdbcTemplate = jdbcTemplate;
  }
  
  public List<Order> findAll()
  {
    return jdbcTemplate.query(
            "select * from order_head",
            new RowMapper<Order>()
            {
              public Order mapRow(ResultSet resultSet, int i)
                      throws SQLException
              {
                return mapFields(resultSet);
              }
            }
    );
  }
  
  private Order get(final String paramName, final Object paramValue)
  {
    return jdbcTemplate.query(
            String.format("select * from order_head where %s = :value", paramName),
            new HashMap<String, Object>(1) {{put(paramName, paramValue);}},
            new ResultSetExtractor<Order>()
            {
              public Order extractData(ResultSet resultSet)
                      throws SQLException, DataAccessException
              {
                return mapFields(resultSet);
              }
            }
    );
  }
  
  public Order getByID(int id)
  {
    return get("id", id);
  }
  
  private Order mapFields(ResultSet resultSet)
          throws SQLException
  {
    Order Order = new Order();
    Order.setId(resultSet.getInt("id"));
    Order.setCreated(resultSet.getDate("created"));
    Order.setIsBasket(resultSet.getInt("is_basket") == 0);
    Order.setNumber(resultSet.getString("order_number"));
//                CommonDAO.setTotal(resultSet.getDouble("total"));
//                CommonDAO.setDetails();
//                CommonDAO.setUser();
    return Order;
  }
  
  public void add(final Order order)
  {
    final int id = generateNewID();
    jdbcTemplate.update("insert into order_head (" +
                        "  id, order_number, created, customer_id, is_basket " +
                        "  ) values (" +
                        "  :id, :order_number, :created, :customer_id, :is_basket)" +
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
    jdbcTemplate.update("delete from order_head where id = :id",
                        new HashMap<String, Object>(){{
                          put("id", order.getId());
                        }});
  }
  
  public int generateNewID()
  {
    return jdbcTemplate.queryForObject("select next value for sq_order", (Map<String, ?>)null, Integer.class);
  }
}
