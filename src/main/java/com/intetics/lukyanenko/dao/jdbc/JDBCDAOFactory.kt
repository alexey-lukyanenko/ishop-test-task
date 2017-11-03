package com.intetics.lukyanenko.dao.jdbc

import com.intetics.lukyanenko.dao.*
import com.intetics.lukyanenko.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class JDBCDAOFactory @Autowired
constructor(
        private val appUserDAO: AppUserDAO,
        private val orderDAO: OrderDAO,
        private val orderDetailDAO: OrderDetailDAO,
        private val goodsItemDAO: GoodsItemDAO,
        private val goodsCategoryDAO: GoodsCategoryDAO,
        private val customerDAO: CustomerDAO
) : DAOFactory {

    override fun <T : Common> getDAO(modelClass: KClass<T>): CommonDAO<T> {
        return when (modelClass) {
            Order::class -> orderDAO
            OrderDetail::class -> orderDetailDAO
            AppUser::class -> appUserDAO
            GoodsCategory::class -> goodsCategoryDAO
            GoodsItem::class -> goodsItemDAO
            Customer::class -> customerDAO
            else -> throw IllegalArgumentException(String.format("Model class %s is not supported", modelClass.simpleName))
        } as CommonDAO<T>
    }
}