package com.intetics.lukyanenko.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebApp
  implements WebApplicationInitializer
{
  @Override
  public void onStartup(ServletContext servletContext) throws ServletException
  {
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(Config.class);
    servletContext.addListener(new ContextLoaderListener(rootContext));
    //
    AnnotationConfigWebApplicationContext dispatcherServletContext = new AnnotationConfigWebApplicationContext();
    dispatcherServletContext.register(DispatcherServletConfig.class);
    ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherServletContext));
    dispatcherServlet.setLoadOnStartup(1);
    dispatcherServlet.addMapping("/");
    
  }
}
