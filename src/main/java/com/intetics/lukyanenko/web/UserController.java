package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/users")
public class UserController
{
  protected final Service service;
  
  @Autowired
  public UserController(Service service)
  {
    this.service = service;
  }
  
  @GetMapping(value = {"", "/"})
  public ModelAndView getList()
  {
    return new ModelAndView("UserList", "list", service.getAppUserList());
  }
  
  @GetMapping(value = "/{name}")
  public ModelAndView get(@PathVariable("name") String name)
  {
    AppUser model = service.getAppUserInfo(name);
    if (model != null)
      return new ModelAndView("UserEdit", "model", model);
    else
      return new ModelAndView("redirect:/users");
  }
 
  @GetMapping(params = "new")
  public ModelAndView getEmptyEditForm()
  {
    return new ModelAndView("UserEdit", "model", service.getAppUserEmptyNew());
  }
  
  @PostMapping(value = "/{name}")
  public String update(@ModelAttribute AppUser appUser)
  {
    appUser.setIsNew(false);
    service.setAppUser(appUser);
    return "redirect:/users";
  }

  @PutMapping
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
  
  @PostMapping(params = "new")
  public ModelAndView insertNew(AppUser appUser)
  {
    return putNew(appUser);
  }
  
  @DeleteMapping(value = "/{name}")
  public void delete(@PathVariable("name") String name)
  {
    service.deleteAppUser(name);
  }
}
