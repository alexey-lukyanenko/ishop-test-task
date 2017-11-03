package com.intetics.lukyanenko.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.view.InternalResourceViewResolver

@Configuration
@EnableWebMvc
@ComponentScan("com.intetics.lukyanenko.web")
open class DispatcherServletConfig : WebMvcConfigurerAdapter() {
    @Bean
    open fun internalJspResolver(): ViewResolver {
        val resolver = InternalResourceViewResolver()
        resolver.setPrefix("/views/")
        resolver.setSuffix(".jsp")
        return resolver
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        super.addResourceHandlers(registry)
        // <mvc:resources mapping="/resources/**" location="/resources/" />
        registry!!.addResourceHandler("/resources/**").addResourceLocations("/resources/")
    }
}
