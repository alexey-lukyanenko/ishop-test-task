package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.Customer;
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
@RequestMapping(value = "/order")
public class OrderController
{
  private final AppService service;
  
  @Inject
  public OrderController(AppService service)
  {
    this.service = service;
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ModelAndView get(@RequestParam Integer id)
  {
    return new ModelAndView("OrderEdit", "model", service.getOrder(id));
  }
  
  @RequestMapping(value = "/{id}/cancel", method = RequestMethod.POST)
  public String cancel(@PathVariable("id") Integer id)
  {
    service.deleteOrder(id);
    return "redirect:/order";
  }
  
  @RequestMapping(value = {"/{id}", ""}, method = RequestMethod.POST)
  public ModelAndView update(@PathVariable("id") Integer orderId,
                             @RequestParam       Map<String, String> params)
  {
    service.updateOrder(orderId, params);
    return new ModelAndView("OrderEdit", "model", service.getOrder(orderId));
  }
  
  @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
  public ModelAndView getList()
  {
    Customer customer = null; // todo Get customer from security context
    return new ModelAndView("OrderList", "model", service.getOrderList(customer));
  }
  
  @RequestMapping(value = "/{orderId}/deleteItem/{itemId}")
  public void deleteItem(@PathVariable("orderId") Integer orderId,
                         @PathVariable("ItemId")  Integer itemId)
  {
    HashMap<String, String> params = new HashMap<>(1);
    params.put(String.valueOf(itemId), "0");
    service.updateOrder(orderId, params);
  }
  
}

