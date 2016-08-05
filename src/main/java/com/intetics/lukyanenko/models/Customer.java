package com.intetics.lukyanenko.models;

public class Customer
extends Common
{
  private int    id;
  private String appUserName;
  private String fullName;
  private String email;
  private String phone;
  private String shippingAddress;
  private String anonymousSessionID;
  
  public int getId()
  {
    return id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getFullName()
  {
    return fullName;
  }
  
  public void setFullName(String fullName)
  {
    this.fullName = fullName;
  }
  
  public String getEmail()
  {
    return email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public String getPhone()
  {
    return phone;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public String getShippingAddress()
  {
    return shippingAddress;
  }
  
  public void setShippingAddress(String shippingAddress)
  {
    this.shippingAddress = shippingAddress;
  }
  
  public String getAnonymousSessionID()
  {
    return anonymousSessionID;
  }
  
  public void setAnonymousSessionID(String anonymousSessionID)
  {
    this.anonymousSessionID = anonymousSessionID;
  }

  public String getAppUserName() {
    return appUserName;
  }

  public void setAppUserName(String appUserName) {
    this.appUserName = appUserName;
  }
}
