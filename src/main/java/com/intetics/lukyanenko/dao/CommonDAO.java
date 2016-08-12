package com.intetics.lukyanenko.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CommonDAO<T>
{
  List<T> findAll();
  void add(T model);
  void update(T model);
  void delete(T model);
  int generateNewID();
  
  T getByID(int id);
  T populateFromResultSet(ResultSet resultSet, String fieldNamePrefix) throws SQLException;
}
