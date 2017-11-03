package com.intetics.lukyanenko.dao

import com.intetics.lukyanenko.models.Common
import kotlin.reflect.KClass

interface DAOFactory {
    fun <T : Common> getDAO(modelClass: KClass<T>): CommonDAO<T>
}
