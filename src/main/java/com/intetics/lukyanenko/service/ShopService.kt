package com.intetics.lukyanenko.service

import com.intetics.lukyanenko.dao.*
import com.intetics.lukyanenko.models.*
import com.intetics.lukyanenko.web.AppService
import javafx.util.Pair
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Service
import java.util.*
import javax.inject.Provider
import kotlin.collections.ArrayList

@Service
@DependsOn(value = "flyway")
class ShopService
@Autowired
constructor(private val factory: DAOFactory) : AppService {

    @Autowired
    internal lateinit var orderProvider: Provider<Order>
    @Autowired
    internal lateinit var orderDetailProvider: Provider<OrderDetail>
    @Autowired
    internal lateinit var customerProvider: Provider<Customer>
    @Autowired
    internal lateinit var goodsCategoryProvider: Provider<GoodsCategory>
    @Autowired
    internal lateinit var goodsItemProvider: Provider<GoodsItem>
    @Autowired
    internal lateinit var appUserProvider: Provider<AppUser>

    private fun newGoodsCategory(): GoodsCategory = goodsCategoryProvider.get()

    private fun newGoodsItem(): GoodsItem = goodsItemProvider.get()

    private fun newOrder(): Order = orderProvider.get()

    private fun newOrderDetail(): OrderDetail = orderDetailProvider.get()

    private fun newAppUser(): AppUser = appUserProvider.get()

    private fun newCustomer(): Customer = customerProvider.get()

    override fun getAppUserList(): List<AppUser> = factory.getDAO(AppUser::class).findAll()

    override fun getAppUserInfo(name: String): AppUser? = (factory.getDAO(AppUser::class) as AppUserDAO).getByName(name)

    override fun setAppUser(appUser: AppUser) {
        with(factory.getDAO(AppUser::class)) {
            if (appUser.isNew)
                add(appUser)
            else
                update(appUser)
        }
    }

    override fun deleteAppUser(appUserName: String) =
            factory.getDAO(AppUser::class).delete(AppUser(appUserName))

    override fun getGoodCategories(): List<GoodsCategory> =
            factory.getDAO(GoodsCategory::class).findAll()

    override fun getGoodItemCategories(goodsItemId: Int): List<GoodsCategory> =
            (factory.getDAO(GoodsCategory::class) as GoodsCategoryDAO).getGoodsItemCategories(goodsItemId)

    override fun getGoodCategory(id: Int): GoodsCategory? = factory.getDAO(GoodsCategory::class).getByID(id)

    override fun setGoodsCategory(goodCategory: GoodsCategory) {
        with(factory.getDAO(GoodsCategory::class)) {
            if (goodCategory.id == null)
                add(goodCategory)
            else
                update(goodCategory)
        }
    }

    override fun deleteGoodsCategory(id: Int) {
        factory.getDAO(GoodsCategory::class).delete(newGoodsCategory().also { it.id = id })
    }

    override fun getGoodsItem(id: Int): GoodsItem? {
        return factory.getDAO(GoodsItem::class).getByID(id)
    }

    override fun getGoodsItemForEdit(id: Int): GoodsItem? {
        return getGoodsItem(id)?.also {
            it.categories = (factory.getDAO(GoodsCategory::class) as GoodsCategoryDAO).getGoodsItemCategories(id)
        }
    }

    override fun getEmptyGoodsItem(): GoodsItem = newGoodsItem()

    override fun setGoodsItem(goodsItem: GoodsItem) {
        with(factory.getDAO(GoodsItem::class)) {
            if (goodsItem.id != null)
                update(goodsItem)
            else
                add(goodsItem)
        }
    }

    override fun deleteGoodsItem(id: Int) {
        factory.getDAO(GoodsItem::class).delete(newGoodsItem().also { it.id = id })
    }

    override fun searchGoodItems(priceFrom: Double?, priceTill: Double?, category: String?, itemName: String?): List<GoodsItem> {
        val params = ArrayList<Pair<String, Pair<String, Any>>>()
        if (priceFrom != null)
            params.add(Pair("price", Pair<String, Any>(">= $(param)", priceFrom)))
        if (priceTill != null)
            params.add(Pair("price", Pair<String, Any>("<= $(param)", priceTill)))
        if (!category.isNullOrEmpty())
            params.add(Pair("category", Pair<String, Any>("like '%' || $(param) || '%'", category)))
        if (!itemName.isNullOrEmpty())
            params.add(Pair("name", Pair<String, Any>("like '%' || $(param) || '%'", itemName)))
        return (factory.getDAO(GoodsItem::class) as GoodsItemDAO).selectWhere(params)
    }

    override fun getBasket(sessionId: String, withDetails: Boolean): Order {
        val dao = factory.getDAO(Order::class) as OrderDAO
        val customer = getSessionCustomer(sessionId)
        return dao.findBasket(customer.id!!)?.also { if (withDetails) fillOrderDetails(it) } ?:
                newOrder()
                        .apply {
                            this.created = Date()
                            this.customer = customer
                            this.isBasket = true
                            this.details = mutableListOf()
                            dao.add(this)
                        }
    }

    private fun getSessionCustomer(sessionId: String): Customer {
        val dao = factory.getDAO(Customer::class) as CustomerDAO
        return dao.find(sessionId) ?: newCustomer()
                .also {
                    it.anonymousSessionID = sessionId
                    dao.add(it)
                }
    }

    override fun updateBasket(basket: Order, updates: ArrayList<OrderDetail>) {
        basket.details = getOrderDetails(basket).apply {
            val dao = factory.getDAO(OrderDetail::class) as OrderDetailDAO
            val userWishes = updates.associateBy { it.id }
            forEach { basketRow ->
                userWishes[basketRow.id]?.let { wish ->
                    if (wish.quantity == 0.0) {
                        dao.delete(wish)
                        remove(basketRow)
                    } else {
                        basketRow.quantity = wish.quantity
                        dao.update(basketRow)
                    }
                }
            }
        }
    }

    override fun checkIfBasketCanBeCheckedOut(basket: Order) {
        if (!basket.isBasket)
            throw IllegalArgumentException("This is not a basket")
        fillOrderDetails(basket)
        if (basket.details?.isEmpty() != false)
            throw IllegalArgumentException("Basket is empty")
        if (basket.customer?.isAnonymous() != false)
            throw AppService.CustomerIsAnonymous()
    }

    override fun addGoodsItemToBasket(goodsItemId: Int, sessionId: String) {
        val basket = getBasket(sessionId)
        val dao = factory.getDAO(OrderDetail::class) as OrderDetailDAO
        dao.findGoodsItemInOrder(basket, goodsItemId)?.also {
            it.quantity++
            dao.update(it)
        } ?: newOrderDetail().also {
            it.order = basket
            it.goodsItem = factory.getDAO(GoodsItem::class).getByID(goodsItemId)
            it.quantity++
            dao.add(it)
        }
    }

    override fun deleteOrder(id: Int) {
        factory.getDAO(Order::class).delete(newOrder().also { it.id = id })
    }

    override fun getOrder(id: Int): Order? = factory.getDAO(Order::class).getByID(id)

    override fun updateOrder(id: Int, params: Map<String, String>) {
        // todo
    }

    override fun getOrderList(customer: Customer): List<Order> {
        // todo
        return ArrayList<Order>(0)
    }

    override fun getCustomerList(): List<Customer> {
        // todo
        return ArrayList<Customer>(0)
    }

    override fun getCustomer(id: Int): Customer? = factory.getDAO(Customer::class).getByID(id)

    override fun setCustomer(customer: Customer) {
        with(factory.getDAO(Customer::class)) {
            if (customer.id == null)
                add(customer)
            else
                update(customer)
        }
    }

    override fun deleteCustomer(id: Int) {
        factory.getDAO(Customer::class).delete(Customer(id))
    }

    override fun clearBasket(sessionId: String) {
        factory.getDAO(Order::class).delete(getBasket(sessionId))
    }

    override fun getOrderDetailEmpty(): OrderDetail = newOrderDetail()

    override fun makeOrderFromBasket(basket: Order): Order {
        checkIfBasketCanBeCheckedOut(basket)
        return (factory.getDAO(Order::class) as OrderDAO).convertBasketToOrder(basket)
    }

    override fun fillOrderDetails(order: Order) {
        order.details = getOrderDetails(order)
    }

    override fun getOrderDetails(order: Order): MutableList<OrderDetail> =
            order.details ?: (factory.getDAO(OrderDetail::class) as OrderDetailDAO).getListForOrder(order)

    override fun getAppUserEmptyNew(): AppUser = newAppUser().also { it.isNew = true }

    override fun getGoodCategoryEmpty(): GoodsCategory = newGoodsCategory()
}