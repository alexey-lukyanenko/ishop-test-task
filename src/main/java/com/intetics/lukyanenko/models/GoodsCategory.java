package com.intetics.lukyanenko.models;

import java.util.List;

public class GoodsCategory extends Common
{
  private Integer         id;
  private String          name;
  private List<GoodsItem> goods;
  
  public Integer getId()
  {
    return id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getName()
  {
    return name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public List<GoodsItem> getGoods()
  {
    return goods;
  }
}
