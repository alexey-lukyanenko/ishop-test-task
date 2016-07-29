package com.intetics.lukyanenko.models;

import com.intetics.lukyanenko.dao.DAOFactory;

public class Common
{
  protected DAOFactory daoFactory;
  
  public void setDaoFactory(DAOFactory daoFactory)
  {
    this.daoFactory = daoFactory;
  }
}
