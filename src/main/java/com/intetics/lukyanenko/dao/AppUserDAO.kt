package com.intetics.lukyanenko.dao

import com.intetics.lukyanenko.models.AppUser

interface AppUserDAO : CommonDAO<AppUser> {
    fun getByName(name: String): AppUser?
}
