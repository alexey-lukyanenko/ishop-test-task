package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.Order;
import com.intetics.lukyanenko.models.OrderDetail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    Order basket = service.getBasket(RequestContextHolder.currentRequestAttributes().getSessionId());
    service.fillOrderDetails(basket);
    //
    ArrayList<OrderDetail> upd = new ArrayList<>();
    Pattern pattern = Pattern.compile("row_(\\d+)");
    for (Map.Entry<String, String> param : params.entrySet())
    {
      Matcher m = pattern.matcher(param.getKey());
      if (m.find())
      {
        OrderDetail row = service.getOrderDetailEmpty();
        row.setId(Integer.valueOf(m.group(1)));
        row.setQuantity(Integer.valueOf(param.getValue()));
        upd.add(row);
      }
    }
    service.updateBasket(basket, upd);
    return new ModelAndView("Basket", "basket", basket);
  }
  
  @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
  public ModelAndView get()
  {
    Order basket = service.getBasket(RequestContextHolder.currentRequestAttributes().getSessionId());
    service.fillOrderDetails(basket);
    return new ModelAndView("Basket", "basket", basket);
  }
  
  @RequestMapping(value = "/short")
  public ModelAndView getShort()
  {
    Order basket = service.getBasket(RequestContextHolder.currentRequestAttributes().getSessionId());
    service.fillOrderDetails(basket);
    return new ModelAndView("BasketInfo", "basket", basket);
  }
  
  @RequestMapping(value = "/checkout", method = RequestMethod.POST)
  public String convertIntoOrder()
  {
    Integer newOrderId = service.tryConvertBasketToOrder();
    if (newOrderId != null)
      return String.format("redirect:/order/%d", newOrderId);
    else
      return "redirect:/customer?new&from_basket";
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void deleteItem(@PathVariable("id") Integer goodsItemId)
  {
    update(new HashMap<String, String>(){{ put(String.format("row_%d", goodsItemId), "0"); }});
  }
  
  @RequestMapping(value = {"", "/"}, method = RequestMethod.DELETE)
  public ModelAndView clear()
  {
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    service.clearBasket(sessionId);
    return new ModelAndView("Basket", "basket", service.getBasket(sessionId));
  }
}
