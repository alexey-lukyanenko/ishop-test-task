package com.intetics.lukyanenko.dao;

import com.intetics.lukyanenko.models.AppUser;

public interface AppUserDAO extends CommonDAO<AppUser>
{
  AppUser getByName(String name);
}
