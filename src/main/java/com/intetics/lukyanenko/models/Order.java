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
  private Customer          customer;
  private Integer           customerId;
  private List<OrderDetail> details;
  
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
    if (customer != null && customer.getId() != customerId)
      customer = null;
  }
  
  public List<OrderDetail> getDetails()
  {
    return details;
  }
  
  public void setDetails(List<OrderDetail> details)
  {
    this.details = details;
  }
  
  public Double getTotal()
  {
    if (details != null)
    {
      double total = 0.0;
      for(OrderDetail row: details)
        total += row.getQuantity() * row.getItemPrice();
      return total;
    }
    else
      return null;
  }
  
  public Customer getCustomer()
  {
    if (customerId == null)
      return null;
    else if (customer == null)
      customer = daoFactory.getDAO(Customer.class).getByID(customerId);
    return customer;
  }
  
  public void setCustomer(Customer customer)
  {
    this.customer = customer;
    this.customerId = customer.getId();
  }
  
}
