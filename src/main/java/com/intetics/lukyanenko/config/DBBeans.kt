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
    open fun dataSource(): SingleConnectionDataSource {
        val ds = SingleConnectionDataSource()
        ds.setDriverClassName("org.h2.Driver")
        ds.url = "jdbc:h2:./ishop"
        ds.username = "ishop"
        ds.password = "ishop"
        ds.setSuppressClose(true)
        return ds
    }

    @Bean
    open fun flyway(dataSource: DataSource): Flyway {
        val obj = Flyway()
        obj.dataSource = dataSource
        obj.setLocations("sql")
        return obj
    }

    @Bean
    open fun jdbcTemplate(dataSource: DataSource): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(dataSource)
    }
}