package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.GoodsItemDAO;
import com.intetics.lukyanenko.models.GoodsItem;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodsItemDAOImpl extends CommonDAOImpl<GoodsItem> implements GoodsItemDAO
{
  public void add(GoodsItem model)
  {
    
  }
  
  public void update(GoodsItem model)
  {
    
  }
  
  public void delete(GoodsItem model)
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
  
  protected GoodsItem mapFields(ResultSet resultSet)
          throws SQLException
  {
    return null;
  }
}
