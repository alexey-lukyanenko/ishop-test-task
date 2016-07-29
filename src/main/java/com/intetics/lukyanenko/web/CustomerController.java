package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
@RequestMapping(value = "/customers")
public class CustomerController
{
  protected final ShopService service;
  
  @Inject
  public CustomerController(ShopService service)
  {
    this.service = service;
  }
  
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ModelAndView getList()
  {
    return new ModelAndView("customerList");
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ModelAndView get(@PathVariable("id") Integer id)
  {
    return new ModelAndView("customerInfo");
  }
  
  
}
