package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.CommonDAO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public abstract class CommonDAOImpl<T> implements CommonDAO<T>
{
  protected NamedParameterJdbcTemplate jdbcTemplate;
  
  public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate)
  {
    this.jdbcTemplate = jdbcTemplate;
  }
  
  protected abstract String getBaseSelectSQL();
  
  public List<T> findAll()
  {
    return jdbcTemplate.query(
            getBaseSelectSQL(),
            new RowMapper<T>()
            {
              public T mapRow(ResultSet resultSet, int i)
                      throws SQLException
              {
                return mapFields(resultSet);
              }
            }
    );
  }
  
  protected abstract T mapFields(ResultSet resultSet)
          throws SQLException;
  
  public T getByID(int id)
  {
    return get("id", id);
  }
  
  protected T get(final String paramName, final Object paramValue)
  {
    return jdbcTemplate.query(
            String.format("%s where %s = :value", getBaseSelectSQL(), paramName),
            new HashMap<String, Object>(1) {{put("value", paramValue);}},
            new ResultSetExtractor<T>()
            {
              public T extractData(ResultSet resultSet)
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
  
}
