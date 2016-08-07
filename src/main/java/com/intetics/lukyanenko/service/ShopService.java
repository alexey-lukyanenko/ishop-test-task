package com.intetics.lukyanenko.service;

import com.intetics.lukyanenko.dao.AppUserDAO;
import com.intetics.lukyanenko.dao.DAOFactory;
import com.intetics.lukyanenko.dao.GoodsCategoryDAO;
import com.intetics.lukyanenko.dao.GoodsItemDAO;
import com.intetics.lukyanenko.models.*;
import com.intetics.lukyanenko.web.Service;
import javafx.util.Pair;

import java.util.*;

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
    return ((GoodsCategoryDAO)factory.getDAO(GoodsCategory.class)).getGoodsItemCategories(goodsItemId);
  }
  
  @Override
  public GoodsCategory getGoodCategory(Integer id)
  {
    return factory.getDAO(GoodsCategory.class).getByID(id);
  }
  
  @Override
  public void setGoodsCategory(GoodsCategory goodCategory)
  {
    if (goodCategory.getId() == null)
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
    return factory.getDAO(GoodsItem.class).getByID(id);
  }
  
  @Override
  public GoodsItem getGoodsItemForEdit(Integer id)
  {
    GoodsItem object = getGoodsItem(id);
    object.setCategories(((GoodsCategoryDAO)factory.getDAO(GoodsCategory.class)).getGoodsItemCategories(id));
    return object;
  }
  
  @Override
  public GoodsItem getEmptyGoodsItem()
  {
    GoodsItem object = new GoodsItem();
    object.setCategories(new ArrayList<>(0));
    return object;
  }
  
  @Override
  public void setGoodsItem(GoodsItem goodsItem)
  {
    if (goodsItem.getId() != null)
      factory.getDAO(GoodsItem.class).update(goodsItem);
    else
      factory.getDAO(GoodsItem.class).add(goodsItem);
  }
  
  @Override
  public void deleteGoodsItem(Integer id)
  {
    GoodsItem object = new GoodsItem();
    object.setId(id);
    factory.getDAO(GoodsItem.class).delete(object);
  }
  
  @Override
  public List<GoodsItem> searchGoodItems(Double priceFrom, Double priceTill, String category, String itemName)
  {
    GoodsItemDAO dao = (GoodsItemDAO) factory.getDAO(GoodsItem.class);
    ArrayList<Pair<String, Pair<String, Object>>> params = new ArrayList<>();
    if (priceFrom != null)
      params.add(new Pair<>("price", new Pair<>(">= $(param)", priceFrom)));
    if (priceTill != null)
      params.add(new Pair<>("price", new Pair<>("<= $(param)", priceTill)));
    if (category != null && !category.isEmpty())
      params.add(new Pair<>("category", new Pair<>("like '%' || $(param) || '%'", category)));
    if (itemName != null && !itemName.isEmpty())
      params.add(new Pair<>("name", new Pair<>("like '%' || $(param) || '%'", itemName)));
    return dao.selectWhere(params);
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
