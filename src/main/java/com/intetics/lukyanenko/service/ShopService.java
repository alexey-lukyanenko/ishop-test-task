package com.intetics.lukyanenko.service;

import com.intetics.lukyanenko.dao.DAOFactory;
import com.intetics.lukyanenko.dao.UserDAO;
import com.intetics.lukyanenko.models.User;

import java.util.LinkedList;
import java.util.List;

public class ShopService
{
  private final DAOFactory factory;
  
  public ShopService(DAOFactory factory) {
    this.factory = factory;
  }
  
  public List<User> getUserList()
  {
    return factory.getDAO(User.class).findAll();
  }
  
  
}
