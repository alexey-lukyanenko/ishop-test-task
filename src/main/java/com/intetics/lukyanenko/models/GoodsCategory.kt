package com.intetics.lukyanenko.models

class GoodsCategory : Common() {
    var id: Int? = null
    var name: String = ""
    val goods: List<GoodsItem> = ArrayList<GoodsItem>(0)
}
