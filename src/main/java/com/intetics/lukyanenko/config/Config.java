package com.intetics.lukyanenko.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ModelPrototypes.class, SpringBeans.class})
public class Config
{
  
}
