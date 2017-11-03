package com.intetics.lukyanenko.dao

import com.intetics.lukyanenko.models.GoodsCategory

interface GoodsCategoryDAO : CommonDAO<GoodsCategory> {
    fun getGoodsItemCategories(goodsItemId: Int): MutableList<GoodsCategory>
}
