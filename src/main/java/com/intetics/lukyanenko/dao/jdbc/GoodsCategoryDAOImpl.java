package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.GoodsCategoryDAO;
import com.intetics.lukyanenko.models.GoodsCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GoodsCategoryDAOImpl extends CommonDAOImpl<GoodsCategory> implements GoodsCategoryDAO
{
  protected String getBaseSelectSQL()
  {
    return null;
  }
  
  public List<GoodsCategory> findAll()
  {
    return null;
  }
  
  protected GoodsCategory mapFields(ResultSet resultSet)
          throws SQLException
  {
    return null;
  }
  
  public GoodsCategory getByID(int id)
  {
    return null;
  }
  
  public void add(GoodsCategory model)
  {
    
  }
  
  public void update(GoodsCategory model)
  {
    
  }
  
  public void delete(GoodsCategory model)
  {
    
  }
  
  public int generateNewID()
  {
    return 0;
  }
}
