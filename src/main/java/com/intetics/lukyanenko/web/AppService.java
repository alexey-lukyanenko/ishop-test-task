package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AppService
{
  List<AppUser> getAppUserList();
  AppUser getAppUserInfo(String name);
  AppUser getAppUserEmptyNew();
  void setAppUser(AppUser appUser);
  void deleteAppUser(String appUserName);
  
  List<GoodsCategory> getGoodCategories();
  List<GoodsCategory> getGoodItemCategories(Integer goodsItemId);
  GoodsCategory getGoodCategory(Integer id);
  GoodsCategory getGoodCategoryEmpty();
  void setGoodsCategory(GoodsCategory goodCategory);
  void deleteGoodsCategory(Integer id);
  
  GoodsItem getGoodsItem(Integer id);
  GoodsItem getGoodsItemForEdit(Integer id);
  GoodsItem getEmptyGoodsItem();
  void setGoodsItem(GoodsItem goodsItem);
  void deleteGoodsItem(Integer id);
  List<GoodsItem> searchGoodItems(Double priceFrom, Double priceTill, String category, String itemName);
  
  Order getBasket(String sessionId);
  
  void updateBasket(Order basket, ArrayList<OrderDetail> updates);
  void checkIfBasketCanBeCheckedOut(Order basket) throws CustomerIsAnonymous;
  void addGoodsItemToBasket(Integer id, String sessionId);
  
  void deleteOrder(Integer id);
  Object getOrder(Integer id);
  void updateOrder(Integer id, Map<String, String> params);
  void fillOrderDetails(Order order);
  List<Order> getOrderList(Customer customer);
  
  List<Customer> getCustomerList();
  Customer getCustomer(Integer id);
  void setCustomer(Customer customer);
  void deleteCustomer(Integer id);
  
  void clearBasket(String sessionId);
  
  OrderDetail getOrderDetailEmpty();
  
  Order makeOrderFromBasket(Order basket) throws CustomerIsAnonymous;
  
  class CustomerIsAnonymous
    extends Exception {}
}
