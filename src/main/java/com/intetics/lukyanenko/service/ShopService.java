package com.intetics.lukyanenko.service;

import com.intetics.lukyanenko.dao.AppUserDAO;
import com.intetics.lukyanenko.dao.DAOFactory;
import com.intetics.lukyanenko.models.*;
import com.intetics.lukyanenko.web.Service;

import java.util.List;
import java.util.Map;

public class ShopService implements Service
{
  private final DAOFactory factory;
  
  public ShopService(DAOFactory factory) {
    this.factory = factory;
  }
  
  @Override
  public List<AppUser> getAppUserList()
  {
    return factory.getDAO(AppUser.class).findAll();
  }
  
  @Override
  public AppUser getAppUserInfo(String name)
  {
    return ((AppUserDAO)factory.getDAO(AppUser.class)).getByName(name);
  }
  
  @Override
  public void setAppUser(AppUser appUser)
  {
    if (appUser.getIsNew())
      factory.getDAO(AppUser.class).add(appUser);
    else
      factory.getDAO(AppUser.class).update(appUser);
  }
  
  public void deleteAppUser(AppUser appUser)
  {
    factory.getDAO(AppUser.class).delete(appUser);
  }
  
  @Override
  public List<GoodsCategory> getGoodCategories()
  {
    return factory.getDAO(GoodsCategory.class).findAll();
  }
  
  @Override
  public List<GoodsCategory> getGoodItemCategories(Integer goodsItemId)
  {
    return factory.getDAO(GoodsCategory.class).search("link.goods_item_id", goodsItemId);
  }
  
  @Override
  public GoodsCategory getGoodCategory(Integer id)
  {
    return factory.getDAO(GoodsCategory.class).getByID(id);
  }
  
  @Override
  public void setGoodsCategory(GoodsCategory goodCategory)
  {
    if (goodCategory.getId() != null)
      factory.getDAO(GoodsCategory.class).add(goodCategory);
    else
      factory.getDAO(GoodsCategory.class).update(goodCategory);
  }
  
  @Override
  public void deleteGoodsCategory(Integer id)
  {
    GoodsCategory object = new GoodsCategory();
    object.setId(id);
    factory.getDAO(GoodsCategory.class).delete(object);
  }
  
  @Override
  public GoodsItem getGoodsItem(Integer id)
  {
    return null;
  }
  
  @Override
  public void setGoodsItem(GoodsItem goodsItem)
  {
    
  }
  
  @Override
  public void deleteGoodsItem(Integer id)
  {
    
  }
  
  @Override
  public List<GoodsItem> searchGoodItems(Map<String, String> searchParams)
  {
    return null;
  }
  
  @Override
  public Order getBasket()
  {
    return null;
  }
  
  @Override
  public void updateBasket(Map<String, String> params)
  {
    
  }
  
  @Override
  public Integer tryConvertBasketToOrder()
  {
    return null;
  }
  
  @Override
  public void addGoodsItemToBasket(Integer id)
  {
    
  }
  
  @Override
  public void deleteOrder(Integer id)
  {
    
  }
  
  @Override
  public Object getOrder(Integer id)
  {
    return null;
  }
  
  @Override
  public void updateOrder(Integer id, Map<String, String> params)
  {
    
  }
  
  @Override
  public List<Order> getOrderList(Customer customer)
  {
    return null;
  }
  
  @Override
  public List<Customer> getCustomerList()
  {
    return null;
  }
  
  @Override
  public Customer getCustomer(Integer id)
  {
    return null;
  }
  
  @Override
  public void setCustomer(Customer customer)
  {
    
  }
  
  @Override
  public void deleteCustomer(Integer id)
  {
    
  }
  
}
