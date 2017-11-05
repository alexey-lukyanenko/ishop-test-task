package com.intetics.lukyanenko.config

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.SingleConnectionDataSource

import javax.sql.DataSource

@Configuration
@ComponentScan("com.intetics.lukyanenko.dao.jdbc")
open class DBBeans {
    @Bean
    open fun dataSource(): SingleConnectionDataSource =
        SingleConnectionDataSource().apply {
            setDriverClassName("org.h2.Driver")
            url = "jdbc:h2:./ishop"
            username = "ishop"
            password = "ishop"
            setSuppressClose(true)
        }

    @Bean
    open fun flyway(dataSource: DataSource): Flyway =
        Flyway().also {
            it.dataSource = dataSource
            it.setLocations("sql")
         }

    @Bean
    open fun jdbcTemplate(dataSource: DataSource): NamedParameterJdbcTemplate
        = NamedParameterJdbcTemplate(dataSource)
}