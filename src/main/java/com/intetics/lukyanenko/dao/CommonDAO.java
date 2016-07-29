package com.intetics.lukyanenko.dao;

import java.util.List;

public interface CommonDAO<T>
{
  List<T> findAll();
  T getByID(int id);
  void add(T model);
  void update(T model);
  void delete(T model);
  int generateNewID();
}
