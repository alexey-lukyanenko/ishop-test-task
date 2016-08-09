package com.intetics.lukyanenko.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController
{
  @RequestMapping({"/"})
  public ModelAndView getDefaultPage()
  {
    return new ModelAndView("DefaultPage");
  }
}

