package com.intetics.lukyanenko.dao;

import java.util.List;

public interface CommonDAO<T>
{
  List<T> findAll();
  void add(T model);
  void update(T model);
  void delete(T model);
  int generateNewID();
  
  T getByID(int id);
}
