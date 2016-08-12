package com.intetics.lukyanenko.models;

import java.lang.ref.WeakReference;

public class OrderDetail extends Common
{
  private int                  id;
  private GoodsItem            goodsItem;
  private WeakReference<Order> order;
  private double               quantity;
  private int                  goodsItemId;
  private int                  orderId;
  private double               itemPrice;
  
  public int getId()
  {
    return id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public GoodsItem getGoodsItem()
  {
    return goodsItem;
  }
  
  public void setGoodsItem(GoodsItem goodsItem)
  {
    this.goodsItem = goodsItem;
    this.goodsItemId = goodsItem.getId();
  }
  
  public Order getOrder()
  {
    return order.get();
  }
  
  public void setOrder(Order order)
  {
    this.order = new WeakReference<Order>(order);
    this.orderId = order.getId();
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
    if (goodsItem != null && goodsItem.getId() != goodsItemId)
      goodsItem = null;
  }
  
  public int getOrderId()
  {
    return orderId;
  }
  
  public void setOrderId(int orderId)
  {
    this.orderId = orderId;
  }
  
  public double getItemPrice()
  {
    if (goodsItem == null)
      return itemPrice;
    else
      return goodsItem.getPrice();
  }
  
  public void setItemPrice(double itemPrice)
  {
    this.itemPrice = itemPrice;
  }
}
