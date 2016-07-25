package com.intetics.lukyanenko.models;

import java.util.List;

public class GoodsItem
{
  private int                 ID;
  private String              Name;
  private String              Description;
  private double              Price;
  private List<GoodsCategory> Categories;
  
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
  
  public String getDescription()
  {
    return Description;
  }
  
  public void setDescription(String description)
  {
    Description = description;
  }
  
  public double getPrice()
  {
    return Price;
  }
  
  public void setPrice(double price)
  {
    Price = price;
  }
  
  public List<GoodsCategory> getCategories()
  {
    return Categories;
  }
}
