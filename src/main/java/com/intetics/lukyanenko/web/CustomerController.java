package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.Customer;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
@RequestMapping("/customer")
public class CustomerController
{
  private final AppService service;
  
  @Inject
  public CustomerController(AppService service)
  {
    this.service = service;
  }
  
  @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
  public ModelAndView getList()
  {
    return new ModelAndView("CustomerList", "list", service.getCustomerList());
  }
  
  @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
  public ModelAndView getEdit(@PathVariable("id") Integer id)
  {
    return new ModelAndView("CustomerEdit", "model", service.getCustomer(id));
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
  public String update(@ModelAttribute Customer customer)
  {
    service.setCustomer(customer);
    return String.format("redirect:/customer/%d", customer.getId());
  }
  
  @RequestMapping(params = "new", method = RequestMethod.GET)
  public ModelAndView getEmptyEditForm()
  {
    return new ModelAndView("CustomerEdit", "model", new Customer());
  }
  
  @RequestMapping(method = RequestMethod.PUT)
  public ModelAndView putNew(Customer customer)
  {
    try
    {
      service.setCustomer(customer);
      return new ModelAndView("redirect:/customer");
    } catch (DuplicateKeyException Exception)
    {
      return new ModelAndView("CustomerEdit", "model", customer);
    }
  }
  
  @RequestMapping(params = "new", method = RequestMethod.POST)
  public ModelAndView insertNew(Customer customer)
  {
    return putNew(customer);
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable("id") Integer id)
  {
    service.deleteCustomer(id);
  }
  
}
