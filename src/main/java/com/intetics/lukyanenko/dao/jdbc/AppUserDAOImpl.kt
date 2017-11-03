package com.intetics.lukyanenko.dao.jdbc

import com.intetics.lukyanenko.dao.AppUserDAO
import com.intetics.lukyanenko.models.AppUser
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.util.*

@Service
class AppUserDAOImpl : CommonDAOImpl<AppUser>(), AppUserDAO {
    private val sqlInsert = """
        insert
          into app_user (
            name,
            password
          ) values (
            :name,
            :password
          )""".trimIndent()
    private val sqlUpdate = """
        update app_user
          set password = :password " +
          where name = :name""".trimIndent()

    private val paramName = "name"
    private val paramPassword = "password"

    override fun add(model: AppUser) {
        jdbcTemplate.update(sqlInsert, mapOf(paramName to model.name, paramPassword to model.password))
    }

    override fun update(model: AppUser) {
        jdbcTemplate.update(sqlUpdate, mapOf(paramName to model.name, paramPassword to model.password))
    }

    override fun delete(model: AppUser) {
        val params = mapOf(paramName to model.name)
        jdbcTemplate.update("delete from app_user_role where user_name = :name", params)
        jdbcTemplate.update("delete from app_user where name = :name", params)
    }

    override fun generateNewID(): Int = 0

    override fun getBaseSelectSQL(): String = """ 
        select au.name,
               (select count(1)
                  from app_user_role aur
                  where aur.user_name = au.name
                    and aur.role_name = 'customer'
               ) is_customer
          from app_user au """.trimIndent()

    override fun mapFields(resultSet: ResultSet, fieldNamePrefix: String): AppUser {
        val appUser = newModelInstance()
        appUser.name = resultSet.getString(fieldNamePrefix + paramName)
        appUser.password = ""
        appUser.isNew = false
        appUser.isCustomer = resultSet.getByte(fieldNamePrefix + "is_customer").compareTo(0) != 0
        return appUser
    }

    override fun getByName(name: String): AppUser? = get(paramName, name.toLowerCase())
}
