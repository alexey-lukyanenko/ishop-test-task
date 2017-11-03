package com.intetics.lukyanenko.web

import com.intetics.lukyanenko.models.*

import java.util.ArrayList

interface AppService {
    fun getAppUserList(): List<AppUser>
    fun getAppUserInfo(name: String): AppUser?
    fun getAppUserEmptyNew(): AppUser
    fun setAppUser(appUser: AppUser)
    fun deleteAppUser(appUserName: String)

    fun getGoodCategories(): List<GoodsCategory>
    fun getGoodItemCategories(goodsItemId: Int): List<GoodsCategory>
    fun getGoodCategory(id: Int): GoodsCategory?
    fun getGoodCategoryEmpty(): GoodsCategory
    fun setGoodsCategory(goodCategory: GoodsCategory)
    fun deleteGoodsCategory(id: Int)

    fun getGoodsItem(id: Int): GoodsItem?
    fun getGoodsItemForEdit(id: Int): GoodsItem?
    fun getEmptyGoodsItem(): GoodsItem
    fun setGoodsItem(goodsItem: GoodsItem)
    fun deleteGoodsItem(id: Int)
    fun searchGoodItems(priceFrom: Double?, priceTill: Double?, category: String?, itemName: String?): List<GoodsItem>

    fun getBasket(sessionId: String, withDetails: Boolean = false): Order

    fun updateBasket(basket: Order, updates: ArrayList<OrderDetail>)

    fun checkIfBasketCanBeCheckedOut(basket: Order)

    fun addGoodsItemToBasket(goodsItemId: Int, sessionId: String)

    fun deleteOrder(id: Int)
    fun getOrder(id: Int): Order?
    fun updateOrder(id: Int, params: Map<String, String>)
    fun fillOrderDetails(order: Order)
    fun getOrderDetails(order: Order): MutableList<OrderDetail>
    fun getOrderList(customer: Customer): List<Order>

    fun getCustomerList(): List<Customer>
    fun getCustomer(id: Int): Customer?
    fun setCustomer(customer: Customer)
    fun deleteCustomer(id: Int)

    fun clearBasket(sessionId: String)

    fun getOrderDetailEmpty(): OrderDetail

    fun makeOrderFromBasket(basket: Order): Order

    class CustomerIsAnonymous : Exception()
}
