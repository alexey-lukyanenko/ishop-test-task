package com.intetics.lukyanenko.models;

public class Customer
{
  private int ID;
  private String FullName;
  private String Email;
  private String Phone;
  private String ShippingAddress;
  private String AnonymousSessionID;
  
  public int getID()
  {
    return ID;
  }
  
  public void setID(int ID)
  {
    this.ID = ID;
  }
  
  public String getFullName()
  {
    return FullName;
  }
  
  public void setFullName(String fullName)
  {
    FullName = fullName;
  }
  
  public String getEmail()
  {
    return Email;
  }
  
  public void setEmail(String email)
  {
    Email = email;
  }
  
  public String getPhone()
  {
    return Phone;
  }
  
  public void setPhone(String phone)
  {
    Phone = phone;
  }
  
  public String getShippingAddress()
  {
    return ShippingAddress;
  }
  
  public void setShippingAddress(String shippingAddress)
  {
    ShippingAddress = shippingAddress;
  }
  
  public String getAnonymousSessionID()
  {
    return AnonymousSessionID;
  }
  
  public void setAnonymousSessionID(String anonymousSessionID)
  {
    AnonymousSessionID = anonymousSessionID;
  }
}
