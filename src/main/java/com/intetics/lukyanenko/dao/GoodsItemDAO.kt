package com.intetics.lukyanenko.dao

import com.intetics.lukyanenko.models.GoodsItem
import javafx.util.Pair

import java.util.ArrayList

interface GoodsItemDAO : CommonDAO<GoodsItem> {
    fun selectWhere(params: ArrayList<Pair<String, Pair<String, Any>>>): List<GoodsItem>
}
