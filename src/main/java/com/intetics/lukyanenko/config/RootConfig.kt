package com.intetics.lukyanenko.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ModelPrototypes::class, DBBeans::class)
@ComponentScan("com.intetics.lukyanenko.service")
open class RootConfig
