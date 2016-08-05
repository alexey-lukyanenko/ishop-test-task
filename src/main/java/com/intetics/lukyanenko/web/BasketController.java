package com.intetics.lukyanenko.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/basket")
public class BasketController
{
  private final Service service;
  
  @Inject
  public BasketController(Service service)
  {
    this.service = service;
  }
  
  @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
  public ModelAndView update(@RequestParam Map<String, String> params)
  {
    service.updateBasket(params);
    return new ModelAndView("Basket", "model", service.getBasket());
  }
  
  @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
  public ModelAndView get()
  {
    return new ModelAndView("Basket", "model", service.getBasket());
  }
  
  @RequestMapping(value = "/short")
  public ModelAndView getShort()
  {
    return new ModelAndView("BasketInfo", "model", service.getBasket());
  }
  
  @RequestMapping(value = "/gotoOrder", method = RequestMethod.POST)
  public String convertIntoOrder()
  {
    Integer newOrderId = service.tryConvertBasketToOrder();
    if (newOrderId != null)
      return String.format("redirect:/order/%d", newOrderId);
    else
      return "redirect:/customer?new&from_basket";
  }
  
  @RequestMapping(value = "/deleteItem/{id}")
  public void deleteItem(@PathVariable("id") Integer goodsItemId)
  {
    HashMap<String, String> params = new HashMap<>(1);
    params.put(String.valueOf(goodsItemId), "0");
    service.updateBasket(params);
  }
}
