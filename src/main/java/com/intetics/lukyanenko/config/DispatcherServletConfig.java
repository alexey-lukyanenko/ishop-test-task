package com.intetics.lukyanenko.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
//@EnableWebSecurity
@ComponentScan({"com.intetics.lukyanenko.web"})
public class DispatcherServletConfig extends WebMvcConfigurerAdapter
//                                     WebMvcConfigurationSupport
{
  @Bean
  ViewResolver internalJspResolver(){
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }
  
  
}
