package com.intetics.lukyanenko.models;

import java.lang.ref.WeakReference;

public class OrderDetail extends Common
{
  private int                  id;
  private GoodsItem            item;
  private WeakReference<Order> order;
  private double               quantity;
  
  public int getId()
  {
    return id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public GoodsItem getItem()
  {
    return item;
  }
  
  public void setItem(GoodsItem item)
  {
    this.item = item;
  }
  
  public Order getOrder()
  {
    return order.get();
  }
  
  public void setOrder(Order order)
  {
    this.order = new WeakReference<Order>(order);
  }
  
  public double getQuantity()
  {
    return quantity;
  }
  
  public void setQuantity(double quantity)
  {
    this.quantity = quantity;
  }
}
