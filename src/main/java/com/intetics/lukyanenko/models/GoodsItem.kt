package com.intetics.lukyanenko.models

class GoodsItem : Common() {
    var id: Int? = null
    var name: String = ""
    var description: String = ""
    var price: Double = 0.toDouble()
    var categories: MutableList<GoodsCategory> = mutableListOf()
}
