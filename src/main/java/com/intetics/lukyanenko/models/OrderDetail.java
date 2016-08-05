package com.intetics.lukyanenko.models;

import java.lang.ref.WeakReference;

public class OrderDetail extends Common
{
  private int                  id;
  private GoodsItem            item;
  private WeakReference<Order> order;
  private double               quantity;
  private int                  goodsItemId;
  private int                  orderId;
  
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
  
  public int getGoodsItemId()
  {
    return goodsItemId;
  }
  
  public void setGoodsItemId(int goodsItemId)
  {
    this.goodsItemId = goodsItemId;
  }
  
  public int getOrderId()
  {
    return orderId;
  }
  
  public void setOrderId(int orderId)
  {
    this.orderId = orderId;
  }
}
