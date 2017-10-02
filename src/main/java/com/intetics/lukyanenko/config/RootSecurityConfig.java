package com.intetics.lukyanenko.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class RootSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().permitAll();
        http.authorizeRequests()
            .antMatchers("/users/**", "/customers/**", "/category/**", "/goods/*/edit").hasAuthority("admin")
            .antMatchers(HttpMethod.POST, "/goods").hasAuthority("admin")
            .antMatchers(HttpMethod.PUT, "/goods").hasAuthority("admin")
            .antMatchers(HttpMethod.DELETE, "/goods").hasAuthority("admin")
            .anyRequest().permitAll();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery("select name, password, true from app_user where name = lower(?)")
            .authoritiesByUsernameQuery("select user_name, role_name from app_user_role where user_name = lower(?)");
    }
}
