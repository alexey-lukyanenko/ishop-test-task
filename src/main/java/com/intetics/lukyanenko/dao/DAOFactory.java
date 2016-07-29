package com.intetics.lukyanenko.dao;

import com.intetics.lukyanenko.models.Common;

public interface DAOFactory
{
  <T extends Common> CommonDAO<T> getDAO(Class<T> modelClass);
}
