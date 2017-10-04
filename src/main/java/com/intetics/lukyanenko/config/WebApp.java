package com.intetics.lukyanenko.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;

public class WebApp
    implements WebApplicationInitializer {
   
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Root (entire application) context and listener for holding this context
        servletContext.addListener(new ContextLoaderListener(newSpringContext(RootConfig.class, RootSecurityConfig.class)));
        //
        // Main servlet and context
        Servlet servlet = new DispatcherServlet(newSpringContext(DispatcherServletConfig.class));
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("dispatcher", servlet);
        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping("/");
        // Spring security filter
        Filter filter = new DelegatingFilterProxy();
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("springSecurityFilterChain", filter);
        filterRegistration.addMappingForUrlPatterns(null, false, "/*");
    }
    
    private AnnotationConfigWebApplicationContext newSpringContext(Class<?>...configClassList) {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(configClassList);
        return rootContext;
    }
}