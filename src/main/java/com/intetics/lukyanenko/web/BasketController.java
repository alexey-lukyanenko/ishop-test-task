package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.Order;
import com.intetics.lukyanenko.models.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

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
  
  @Autowired
  public BasketController(Service service)
  {
    this.service = service;
  }
  
  @PostMapping(value = {"/", ""})
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
  
  @GetMapping(value = {"/", ""})
  public ModelAndView get()
  {
    Order basket = service.getBasket(RequestContextHolder.currentRequestAttributes().getSessionId());
    service.fillOrderDetails(basket);
    return new ModelAndView("Basket", "basket", basket);
  }
  
  @GetMapping(value = "/short")
  public ModelAndView getShort()
  {
    Order basket = service.getBasket(RequestContextHolder.currentRequestAttributes().getSessionId());
    service.fillOrderDetails(basket);
    return new ModelAndView("BasketInfo", "basket", basket);
  }
  
  @PostMapping(value = "/checkout")
  public String Checkout()
  {
    Order basket = service.getBasket(RequestContextHolder.currentRequestAttributes().getSessionId());
    try
    {
      service.checkIfBasketCanBeCheckedOut(basket);
      return "redirect:/basket/confirm-check-out";
    } catch (Service.CustomerIsAnonymous E)
      {
        return "redirect:/customer?new&from_basket";
      }
      catch (IllegalArgumentException E)
      {
        return "redirect:/InvalidRequest"; //todo check if it is correct instruction
      }
  }
  
  @GetMapping(value = "confirm-check-out")
  public ModelAndView ViewCheckoutConfirmation()
  {
    return new ModelAndView("BasketConfirmCheckout");
  }
  
  @PostMapping(value = "confirm-check-out")
  public String ConfirmCheckout()
  {
    try
    {
      Order newOrder = service.makeOrderFromBasket(service.getBasket(RequestContextHolder
                                                                       .currentRequestAttributes()
                                                                       .getSessionId()));
      return String.format("redirect:/order/%d?congrats", newOrder.getId());
    } catch (Throwable E)
      {
        return "redirect:/InvalidRequest";
      }
  }
  
  @DeleteMapping(value = "/{id}")
  public void deleteItem(@PathVariable("id") Integer goodsItemId)
  {
    update(new HashMap<String, String>(){{ put(String.format("row_%d", goodsItemId), "0"); }});
  }
  
  @DeleteMapping(value = {"", "/"})
  public ModelAndView clear()
  {
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    service.clearBasket(sessionId);
    return new ModelAndView("Basket", "basket", service.getBasket(sessionId));
  }
}
