package com.intetics.lukyanenko.service;

import com.intetics.lukyanenko.dao.AppUserDAO;
import com.intetics.lukyanenko.dao.DAOFactory;
import com.intetics.lukyanenko.models.AppUser;
import com.intetics.lukyanenko.web.Service;

import java.util.List;

public class ShopService implements Service
{
  private final DAOFactory factory;
  
  public ShopService(DAOFactory factory) {
    this.factory = factory;
  }
  
  public List<AppUser> getAppUserList()
  {
    return factory.getDAO(AppUser.class).findAll();
  }
  
  public AppUser getAppUserInfo(String name)
  {
    return ((AppUserDAO)factory.getDAO(AppUser.class)).getByName(name);
  }
  
  public void setAppUser(AppUser appUser)
  {
    if (appUser.getIsNew())
      factory.getDAO(AppUser.class).add(appUser);
    else
      factory.getDAO(AppUser.class).update(appUser);
  }
  
  public void deleteAppUser(AppUser appUser)
  {
    factory.getDAO(AppUser.class).delete(appUser);
  }
  
}
