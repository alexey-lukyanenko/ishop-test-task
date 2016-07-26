package com.intetics.lukyanenko.models;

import java.util.Date;
import java.util.List;

public class Order
{
  private int               id;
  private String            number;
  private Date              created;
  private boolean           isBasket;
  private Customer          customer;
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
  
  public Customer getCustomer()
  {
    return customer;
  }
  
  public void setCustomer(Customer customer)
  {
    this.customer = customer;
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
}
