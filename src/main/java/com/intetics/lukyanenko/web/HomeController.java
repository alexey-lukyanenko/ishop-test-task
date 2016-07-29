package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;

@Controller
public class HomeController
{
  protected final ShopService service;
  
  @Inject
  HomeController(ShopService service)
  {
    this.service = service;
  }
  
  @RequestMapping({"/", "/default"})
  public ModelAndView index()
  {
    return new ModelAndView("home", "home",
                            new HashMap<String, String>(){{
                              put("title", "The title of the page");
                              put("text", "simple text");
    }});
  }
  
}

