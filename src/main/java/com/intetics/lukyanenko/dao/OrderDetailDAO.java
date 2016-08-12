package com.intetics.lukyanenko.dao;

import com.intetics.lukyanenko.models.Order;
import com.intetics.lukyanenko.models.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO
        extends CommonDAO<OrderDetail>
{
  OrderDetail findGoodsItemInOrder(Order order, Integer goodsItemId);
  
  List<OrderDetail> getListForOrder(Order order, SubdetailsFillable subDetails);
  
  interface SubdetailsFillable
  {
    void fill(OrderDetail detail, ResultSet resultSet, String fieldNamePrefix) throws SQLException;
  }
}
