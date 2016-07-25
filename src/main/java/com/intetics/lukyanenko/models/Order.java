package com.intetics.lukyanenko.models;

import java.util.Date;
import java.util.List;

public class Order
{
  private int               ID;
  private String            Number;
  private Date              Created;
  private boolean           IsBasket;
  private Customer          customer;
  private List<OrderDetail> Details;
  private double            Total;
  
  public int getID()
  {
    return ID;
  }
  
  public void setID(int ID)
  {
    this.ID = ID;
  }
  
  public String getNumber()
  {
    return Number;
  }
  
  public void setNumber(String number)
  {
    Number = number;
  }
  
  public Date getCreated()
  {
    return Created;
  }
  
  public void setCreated(Date created)
  {
    Created = created;
  }
  
  public boolean isBasket()
  {
    return IsBasket;
  }
  
  public void setBasket(boolean basket)
  {
    IsBasket = basket;
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
    return Details;
  }
  
  public void setDetails(List<OrderDetail> details)
  {
    Details = details;
  }
  
  public double getTotal()
  {
    return Total;
  }
  
  public void setTotal(double total)
  {
    Total = total;
  }
}
