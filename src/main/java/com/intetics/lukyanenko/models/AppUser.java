package com.intetics.lukyanenko.models;

import java.util.List;

public class AppUser extends Common
{
  private String name;
  private String password;
  private boolean isCustomer;
  private List<String> roles;
  private boolean isNew;
  
  public AppUser()
  {
    super();
    isNew = true;
  }
  
  public AppUser(String name)
  {
    super();
    isNew = false;
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public List<String> getRoles() {
    return roles;
  }
  
  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
  
  public boolean getIsCustomer()
  {
    return isCustomer;
  }
  
  public void setIsCustomer(boolean customer)
  {
    isCustomer = customer;
  }
  
  public boolean getIsNew()
  {
    return isNew;
  }
  
  public boolean isNew()
  {
    return isNew;
  }
  
  public void setNew(boolean isNew)
  {
    this.isNew = isNew;
  }
}
