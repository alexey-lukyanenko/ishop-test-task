package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.*;

import java.util.List;
import java.util.Map;

public interface Service
{
  List<AppUser> getAppUserList();
  AppUser getAppUserInfo(String name);
  void setAppUser(AppUser appUser);
  void deleteAppUser(AppUser appUser);
  
  List<GoodsCategory> getGoodCategories();
  List<GoodsCategory> getGoodItemCategories(Integer goodsItemId);
  GoodsCategory getGoodCategory(Integer id);
  void setGoodsCategory(GoodsCategory goodCategory);
  void deleteGoodsCategory(Integer id);
  
  GoodsItem getGoodsItem(Integer id);
  void setGoodsItem(GoodsItem goodsItem);
  void deleteGoodsItem(Integer id);
  List<GoodsItem> searchGoodItems(Map<String, String> searchParams);
  
  Order getBasket();
  void updateBasket(Map<String, String> params);
  Integer tryConvertBasketToOrder();
  void addGoodsItemToBasket(Integer id);
  
  void deleteOrder(Integer id);
  Object getOrder(Integer id);
  void updateOrder(Integer id, Map<String, String> params);
  List<Order> getOrderList(Customer customer);
  
  List<Customer> getCustomerList();
  Customer getCustomer(Integer id);
  void setCustomer(Customer customer);
  void deleteCustomer(Integer id);
}
