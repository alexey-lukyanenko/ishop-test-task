package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.OrderDetailDAO;
import com.intetics.lukyanenko.models.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailDAOImpl
        extends CommonDAOImpl<OrderDetail>
        implements OrderDetailDAO
{
  public void add(OrderDetail model)
  {
    
  }
  
  public void update(OrderDetail model)
  {
    
  }
  
  public void delete(OrderDetail model)
  {
    
  }
  
  public int generateNewID()
  {
    return 0;
  }
  
  protected String getBaseSelectSQL()
  {
    return null;
  }
  
  protected OrderDetail mapFields(ResultSet resultSet)
          throws SQLException
  {
    return null;
  }
}
