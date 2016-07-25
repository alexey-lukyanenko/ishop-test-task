package com.intetics.lukyanenko.models;

import java.lang.ref.WeakReference;

public class OrderDetail
{
  private int ID;
  private GoodsItem Item;
  private WeakReference<Order> Order;
  private double Quantity;
  
  public int getID()
  {
    return ID;
  }
  
  public void setID(int ID)
  {
    this.ID = ID;
  }
  
  public GoodsItem getItem()
  {
    return Item;
  }
  
  public void setItem(GoodsItem item)
  {
    Item = item;
  }
  
  public Order getOrder()
  {
    return Order.get();
  }
  
  public void setOrder(Order order)
  {
    Order = new WeakReference<Order>(order);
  }
  
  public double getQuantity()
  {
    return Quantity;
  }
  
  public void setQuantity(double quantity)
  {
    Quantity = quantity;
  }
}
