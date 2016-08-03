package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.AppUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
@RequestMapping(value = "/users")
public class UserController
{
  protected final Service service;
  
  @Inject
  public UserController(Service service)
  {
    this.service = service;
  }
  
  @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
  public ModelAndView getList()
  {
    return new ModelAndView("UserList", "list", service.getAppUserList());
  }
  
  @RequestMapping(value = "/{name}", method = RequestMethod.GET)
  public ModelAndView get(@PathVariable("name") String name)
  {
    return new ModelAndView("UserEdit", "model", service.getAppUserInfo(name));
  }
 
  @RequestMapping(params = "new", method = RequestMethod.GET)
  public ModelAndView getEmptyEditForm()
  {
    return new ModelAndView("UserEdit", "model", new AppUser());
  }
  
  @RequestMapping(value = "/{name}", method = RequestMethod.POST)
  public String update(AppUser appUser)
  {
    service.setAppUser(appUser);
    return "redirect:/users";
  }

  @RequestMapping(method = RequestMethod.PUT)
  public String insertNew(AppUser appUser)
  {
    service.setAppUser(appUser);
    return "redirect:/users";
  }
  
  @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
  public String  delete(@PathVariable("name") String name)
  {
    service.deleteAppUser(new AppUser(name));
    return "redirect:/users";
  }
}
