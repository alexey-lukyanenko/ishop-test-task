package com.intetics.lukyanenko.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;

@Configuration
public class SpringBeans
{
  @Bean
  SingleConnectionDataSource dataSource(){
    SingleConnectionDataSource ds = new SingleConnectionDataSource();
    ds.setDriverClassName("org.h2.Driver");
    ds.setUrl("jdbc:h2:./ishop");
    ds.setUsername("ishop");
    ds.setPassword("ishop");
    ds.setSuppressClose(true);
    return ds;
  }
  
  @Bean
  Flyway flyway(DataSource dataSource){
    Flyway obj = new Flyway();
    obj.setDataSource(dataSource);
    obj.setLocations("sql");
    return obj;
  }
  
  @Bean
  NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource){
    return new NamedParameterJdbcTemplate(dataSource);
  }
  
  
}
