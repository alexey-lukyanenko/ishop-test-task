package com.intetics.lukyanenko.models;

import java.util.ArrayList;
import java.util.List;

public class GoodsItem extends Common
{
  private Integer                 id;
  private String              name;
  private String              description;
  private double              price;
  private List<GoodsCategory> categories;
  
  public GoodsItem()
  {
    this.categories = null;
  }
  
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
  
  public String getDescription()
  {
    return description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public double getPrice()
  {
    return price;
  }
  
  public void setPrice(double price)
  {
    this.price = price;
  }
  
  public List<GoodsCategory> getCategories()
  {
    return categories;
  }
  
  public void setCategories(List<GoodsCategory> categories) {
    this.categories = categories;
  }
}
