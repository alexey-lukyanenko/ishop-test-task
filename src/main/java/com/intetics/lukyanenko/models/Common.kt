package com.intetics.lukyanenko.models

import com.intetics.lukyanenko.dao.DAOFactory
import org.springframework.beans.factory.annotation.Autowired

abstract class Common {
    @Autowired
    protected lateinit var daoFactory: DAOFactory
}
