package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.UserDAO;
import com.intetics.lukyanenko.models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate)
  {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<User> findAll()
  {
    return jdbcTemplate.query(
            "select * from app_user",
            new RowMapper<User>()
            {
              public User mapRow(ResultSet resultSet, int i)
                      throws SQLException
              {
                return mapFields(resultSet);
              }
            });
  }
  
  private User mapFields(ResultSet resultSet)
  {
    return null;
  }
  
  public User getByID(int id)
  {
    return null;
  }
  
  public void add(User model)
  {
    
  }
  
  public void update(User model)
  {
    
  }
  
  public void delete(User model)
  {
    
  }
  
  public int generateNewID()
  {
    return 0;
  }
}
