package com.intetics.lukyanenko.models;

import java.util.List;

public class GoodsCategory
{
  private int             ID;
  private String          Name;
  private List<GoodsItem> Goods;
  
  public int getID()
  {
    return ID;
  }
  
  public void setID(int ID)
  {
    this.ID = ID;
  }
  
  public String getName()
  {
    return Name;
  }
  
  public void setName(String name)
  {
    Name = name;
  }
  
  public List<GoodsItem> getGoods()
  {
    return Goods;
  }
}
