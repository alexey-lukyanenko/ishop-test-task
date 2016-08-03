package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.AppUser;

import java.util.List;

public interface Service
{
  public List<AppUser> getAppUserList();
  
  AppUser getAppUserInfo(String name);
  
  void setAppUser(AppUser appUser);
  
  void deleteAppUser(AppUser appUser);
}
