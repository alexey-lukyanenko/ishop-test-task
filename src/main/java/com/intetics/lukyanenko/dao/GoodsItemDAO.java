package com.intetics.lukyanenko.dao;

import com.intetics.lukyanenko.models.GoodsItem;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public interface GoodsItemDAO
        extends CommonDAO<GoodsItem>
{
  List<GoodsItem> selectWhere(ArrayList<Pair<String, Pair<String, Object>>> params);
}
