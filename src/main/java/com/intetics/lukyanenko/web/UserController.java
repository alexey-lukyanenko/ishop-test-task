package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.AppUser;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    AppUser model = service.getAppUserInfo(name);
    if (model != null)
      return new ModelAndView("UserEdit", "model", model);
    else
      return new ModelAndView("redirect:/users");
  }
 
  @RequestMapping(params = "new", method = RequestMethod.GET)
  public ModelAndView getEmptyEditForm()
  {
    return new ModelAndView("UserEdit", "model", new AppUser(true));
  }
  
  @RequestMapping(value = "/{name}", method = RequestMethod.POST)
  public String update(@ModelAttribute AppUser appUser)
  {
    appUser.setIsNew(false);
    service.setAppUser(appUser);
    return "redirect:/users";
  }

  @RequestMapping(method = RequestMethod.PUT)
  public ModelAndView putNew(AppUser appUser)
  {
    try
    {
      appUser.setIsNew(true);
      service.setAppUser(appUser);
      return new ModelAndView("redirect:/users");
    } catch (DuplicateKeyException Exception)
    {
      return new ModelAndView("UserEdit", "model", appUser);
    }
  }
  
  @RequestMapping(params = "new", method = RequestMethod.POST)
  public ModelAndView insertNew(AppUser appUser)
  {
    return putNew(appUser);
  }
  
  @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
  public void delete(@PathVariable("name") String name)
  {
    service.deleteAppUser(new AppUser(name));
  }
}
