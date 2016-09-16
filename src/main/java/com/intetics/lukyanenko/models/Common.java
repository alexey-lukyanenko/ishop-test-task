package com.intetics.lukyanenko.models;

import com.intetics.lukyanenko.dao.DAOFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Common
{
  protected DAOFactory daoFactory;
  
  @Autowired
  public void setDaoFactory(DAOFactory daoFactory)
  {
    this.daoFactory = daoFactory;
  }
}
