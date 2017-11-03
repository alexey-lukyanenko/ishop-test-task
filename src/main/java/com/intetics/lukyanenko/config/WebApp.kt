package com.intetics.lukyanenko.config

import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.filter.DelegatingFilterProxy
import org.springframework.web.servlet.DispatcherServlet

import javax.servlet.*

class WebApp : WebApplicationInitializer {

    override fun onStartup(servletContext: ServletContext) {
        // Root (entire application) context and listener for holding this context
        servletContext.addListener(ContextLoaderListener(newSpringContext(RootConfig::class.java, RootSecurityConfig::class.java)))
        //
        // Main servlet and context
        val servlet = DispatcherServlet(newSpringContext(DispatcherServletConfig::class.java))
        val servletRegistration = servletContext.addServlet("dispatcher", servlet)
        servletRegistration.setLoadOnStartup(1)
        servletRegistration.addMapping("/")
        // Spring security filter
        val filter = DelegatingFilterProxy()
        val filterRegistration = servletContext.addFilter("springSecurityFilterChain", filter)
        filterRegistration.addMappingForUrlPatterns(null, false, "/*")
    }

    private fun newSpringContext(vararg configClassList: Class<*>): AnnotationConfigWebApplicationContext {
        val rootContext = AnnotationConfigWebApplicationContext()
        rootContext.register(*configClassList)
        return rootContext
    }
}