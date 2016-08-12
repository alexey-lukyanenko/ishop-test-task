package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.*;
import com.intetics.lukyanenko.models.*;

public class JDBCDAOFactory implements DAOFactory
{
  private final AppUserDAO appUserDAO;
  private final OrderDAO orderDAO;
  private final OrderDetailDAO orderDetailDAO;
  private final GoodsItemDAO goodsItemDAO;
  private final GoodsCategoryDAO goodsCategoryDAO;
  private final CustomerDAO customerDAO;
  
  public JDBCDAOFactory(
                         AppUserDAO appUserDAO,
                         OrderDAO orderDAO,
                         OrderDetailDAO orderDetailDAO,
                         GoodsItemDAO goodsItemDAO,
                         GoodsCategoryDAO goodsCategoryDAO,
                         CustomerDAO customerDAO
                       )
  {
    this.appUserDAO = appUserDAO;
    this.orderDAO = orderDAO;
    this.orderDetailDAO = orderDetailDAO;
    this.goodsItemDAO = goodsItemDAO;
    this.goodsCategoryDAO = goodsCategoryDAO;
    this.customerDAO = customerDAO;
  }
  
  public <T extends Common> CommonDAO<T> getDAO(Class<T> modelClass)
  {
    if (modelClass == Order.class)
      return (CommonDAO<T>)orderDAO;
    else if (modelClass == OrderDetail.class)
      return (CommonDAO<T>)orderDetailDAO;
    else if (modelClass == AppUser.class)
      return (CommonDAO<T>)appUserDAO;
    else if (modelClass == GoodsCategory.class)
      return (CommonDAO<T>)goodsCategoryDAO;
    else if (modelClass == GoodsItem.class)
      return (CommonDAO<T>)goodsItemDAO;
    else if (modelClass == Customer.class)
      return (CommonDAO<T>)customerDAO;
    else
      throw new IllegalArgumentException(String.format("Model class %s is not supported", modelClass.getSimpleName()));
  }
}
