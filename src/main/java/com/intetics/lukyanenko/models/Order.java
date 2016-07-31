package com.intetics.lukyanenko.models;

import java.util.Date;
import java.util.List;

public class Order
        extends Common
{
  private int               id;
  private String            number;
  private Date              created;
  private boolean           isBasket;
  private User user;
  private Integer           customerId;
  private List<OrderDetail> details;
  private double            total;
  
  public int getId()
  {
    return id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getNumber()
  {
    return number;
  }
  
  public void setNumber(String number)
  {
    this.number = number;
  }
  
  public Date getCreated()
  {
    return created;
  }
  
  public void setCreated(Date created)
  {
    this.created = created;
  }
  
  public boolean getIsBasket()
  {
    return isBasket;
  }
  
  public void setIsBasket(boolean isBasket)
  {
    this.isBasket = isBasket;
  }
  
  public Integer getCustomerId()
  {
    return customerId;
  }
  
  public void setCustomerId(int customerId)
  {
    this.customerId = customerId;
    if (user != null && user.getId() != customerId)
      user = null;
  }
  
  public List<OrderDetail> getDetails()
  {
    return details;
  }
  
  public void setDetails(List<OrderDetail> details)
  {
    this.details = details;
  }
  
  public double getTotal()
  {
    return total;
  }
  
  public void setTotal(double total)
  {
    this.total = total;
  }
  
  public User getUser()
  {
    if (customerId == null)
      return null;
    else if (user == null)
      user = daoFactory.getDAO(User.class).getByID(customerId);
    return user;
  }
  
  public void setUser(User user)
  {
    this.user = user;
    this.customerId = user.getId();
  }
  
}
