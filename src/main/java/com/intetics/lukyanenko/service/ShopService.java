package com.intetics.lukyanenko.service;

import com.intetics.lukyanenko.dao.*;
import com.intetics.lukyanenko.models.*;
import com.intetics.lukyanenko.web.Service;
import javafx.util.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
  
  public void deleteAppUser(String appUserName)
  {
    factory.getDAO(AppUser.class).delete(new AppUser(appUserName));
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
  public Order getBasket(String sessionId)
  {
    OrderDAO dao = (OrderDAO)factory.getDAO(Order.class);
    Customer customer = getSessionCustomer(sessionId);
    Order basket = dao.findBasket(customer.getId());
    if (basket == null)
    {
      basket = new Order();
      basket.setCreated(new Date());
      basket.setCustomer(customer);
      basket.setIsBasket(true);
      dao.add(basket);
    }
    return basket;
  }
  
  private Customer getSessionCustomer(String sessionId)
  {
    CustomerDAO dao = (CustomerDAO)factory.getDAO(Customer.class);
    Customer customer = dao.find(sessionId);
    if (customer == null)
    {
      customer = new Customer();
      customer.setAnonymousSessionID(sessionId);
      dao.add(customer);
    }
    return customer;
  }
  
  @Override
  public void updateBasket(Order basket, ArrayList<OrderDetail> updates)
  {
    if (basket.getDetails() == null)
      fillOrderDetails(basket);
    //
    OrderDetailDAO dao = (OrderDetailDAO)factory.getDAO(OrderDetail.class);
    for(OrderDetail upd : updates)
      for(OrderDetail row : basket.getDetails())
        if (row.getId() == upd.getId())
          if (upd.getQuantity() == 0)
          {
            dao.delete(upd);
            basket.getDetails().remove(row);
          }
          else
          {
            row.setQuantity(upd.getQuantity());
            dao.update(row);
          }
  }
  
  @Override
  public Integer tryConvertBasketToOrder()
  {
    return null;
    // todo tryConvertBasketToOrder
  }
  
  @Override
  public void addGoodsItemToBasket(Integer goodsItemid, String sessionId)
  {
    Order basket = getBasket(sessionId);
    OrderDetailDAO dao = (OrderDetailDAO)factory.getDAO(OrderDetail.class);
    OrderDetail detail = dao.findGoodsItemInOrder(basket, goodsItemid);
    if (detail == null)
    {
      detail = new OrderDetail();
      detail.setOrder(basket);
      detail.setGoodsItem(factory.getDAO(GoodsItem.class).getByID(goodsItemid));
      detail.setQuantity(1);
      dao.add(detail);
    }
    else
    {
      detail.setQuantity(detail.getQuantity() + 1);
      dao.update(detail);
    }
  }
  
  @Override
  public void deleteOrder(Integer id)
  {
    Order object = new Order();
    object.setId(id);
    factory.getDAO(Order.class).delete(object);
  }
  
  @Override
  public Object getOrder(Integer id)
  {
    return factory.getDAO(Order.class).getByID(id);
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
  
  @Override
  public void clearBasket(String sessionId)
  {
    factory.getDAO(Order.class).delete(getBasket(sessionId));
  }
  
  @Override
  public OrderDetail getOrderDetailEmpty()
  {
    return new OrderDetail();
  }
  
  @Override
  public void fillOrderDetails(Order order)
  {
    order.setDetails(((OrderDetailDAO)factory.getDAO(OrderDetail.class))
                       .getListForOrder(order,
                                        new OrderDetailDAO.SubdetailsFillable() {
                                          @Override
                                          public void fill(OrderDetail detail, ResultSet resultSet,
                                                           String fieldNamePrefix
                                                          ) throws SQLException
                                          {
                                            detail.setGoodsItem(factory.getDAO(GoodsItem.class)
                                                                       .populateFromResultSet(resultSet, fieldNamePrefix)
                                                               );
                                          }
                                        }));
  }
  
  @Override
  public AppUser getAppUserEmptyNew()
  {
    return new AppUser(true);
  }
  
  @Override
  public GoodsCategory getGoodCategoryEmpty()
  {
    return new GoodsCategory();
  }
  
}
