package com.intetics.lukyanenko.dao.jdbc;

import com.intetics.lukyanenko.dao.CommonDAO;
import com.intetics.lukyanenko.dao.DAOFactory;
import com.intetics.lukyanenko.models.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class JDBCDAOFactory implements DAOFactory, ApplicationContextAware
{
  private ApplicationContext context;
  
  public <T extends Common> CommonDAO<T> getDAO(Class<T> modelClass)
  {
    if (modelClass == Order.class)
      return (CommonDAO<T>)context.getBean(OrderDAOImpl.class);
    else if (modelClass == OrderDetail.class)
      return (CommonDAO<T>)context.getBean(OrderDetailDAOImpl.class);
    else if (modelClass == User.class)
      return (CommonDAO<T>)context.getBean(UserDAOImpl.class);
    else if (modelClass == GoodsCategory.class)
      return (CommonDAO<T>)context.getBean(GoodsCategoryDAOImpl.class);
    else if (modelClass == GoodsItem.class)
      return (CommonDAO<T>)context.getBean(GoodsItemDAOImpl.class);
    else
      throw new IllegalArgumentException(String.format("Model class %s is not supported", modelClass.getSimpleName()));
  }
  
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
  {
     context = applicationContext;
  }
}
