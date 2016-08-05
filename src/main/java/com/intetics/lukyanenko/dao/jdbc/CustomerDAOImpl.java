package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.CustomerDAO;
import com.intetics.lukyanenko.models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CustomerDAOImpl
  extends CommonDAOImpl<Customer>
  implements CustomerDAO
{
  @Override
  protected String getBaseSelectSQL()
  {
    return "select * from customer";
  }
  
  @Override
  protected Customer mapFields(ResultSet resultSet)
  throws SQLException
  {
    Customer object = new Customer();
    object.setId(resultSet.getInt("id"));
//    object.setAnonymousSessionID(resultSet.getString("anonymous_session_id"));
    object.setAppUserName(resultSet.getString("app_user_name"));
    object.setEmail(resultSet.getString("email"));
    object.setPhone(resultSet.getString("phone"));
    object.setShippingAddress(resultSet.getString("shipping_address"));
    return object;
  }
  
  public void add(Customer model)
  {
    HashMap<String, Object> params = new HashMap<>();
    int id = generateNewID();
    params.put("id", id);
    params.put("app_user_name", model.getAppUserName());
    params.put("email", model.getEmail());
    params.put("phone", model.getPhone());
    params.put("shipping_address", model.getShippingAddress());
    params.put("anonymous_session_id", model.getAnonymousSessionID());
    jdbcTemplate.update("insert " +
                        "  into customer (" +
                        "    id," +
                        "    app_user_name," +
                        "    email," +
                        "    phone," +
                        "    shipping_address," +
                        "    anonymous_session_id" +
                        "  ) values (" +
                        "    :id," +
                        "    :app_user_name," +
                        "    :email," +
                        "    :phone," +
                        "    :shipping_address," +
                        "    :anonymous_session_id" +
                        "  )",
                        params
    );
    model.setId(id);
  }
  
  public void update(Customer model)
  {
    HashMap<String, Object> params = new HashMap<>();
    params.put("id", model.getId());
    params.put("app_user_name", model.getAppUserName());
    params.put("email", model.getEmail());
    params.put("phone", model.getPhone());
    params.put("shipping_address", model.getShippingAddress());
    params.put("anonymous_session_id", model.getAnonymousSessionID());
    jdbcTemplate.update("update customer (" +
                        "  set app_user_name    = :app_user_name," +
                        "      email            = :email," +
                        "      phone            = :phone," +
                        "      shipping_address = :shipping_address," +
                        "      anonymous_session_id = :anonymous_session_id" +
                        "  where id = :id",
                        params
    );
  }
  
  public void delete(Customer model)
  {
    HashMap<String, Object> params = new HashMap<>();
    params.put("id", model.getId());
    jdbcTemplate.update("delete from customer (" +
                        "  where id = :id" +
                        "    and not exists (select * " +
                        "                      from order_head" +
                        "                      where customer_id = :id)",
                        params
    );
  }
  
  public int generateNewID()
  {
    return jdbcTemplate.queryForObject("select next value for sq_customer",
                                       (HashMap<String, Object>)null,
                                       Integer.class);
  }
}
