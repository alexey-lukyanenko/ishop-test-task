package com.intetics.lukyanenko.dao;

import com.intetics.lukyanenko.models.GoodsCategory;

import java.util.List;

public interface GoodsCategoryDAO
        extends CommonDAO<GoodsCategory>
{
  List<GoodsCategory> getGoodsItemCategories(Integer goodsItemId);
}
