package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.GoodsCategory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;

@Controller
@RequestMapping(value = "/category")
public class GoodsCategoryController
{
  private final Service service;
  
  @Inject
  public GoodsCategoryController(Service service)
  {
    this.service = service;
  }
  
  @RequestMapping(value = {"/", ""})
  public ModelAndView listAll()
  {
    return new ModelAndView("GoodsCategoryList", "list", service.getGoodCategories());
  }
  
  @RequestMapping(params = "ofitem")
  public ModelAndView listForGoodItem(@PathVariable(value = "ofitem") Integer goodsItemId)
  {
    return new ModelAndView("GoodsCategoryList", "list_only", service.getGoodItemCategories(goodsItemId));
  }
  
  @RequestMapping(params = {"ofitem", "select"})
  public ModelAndView listForGoodItem2(@PathVariable(value = "ofitem") Integer goodsItemId)
  {
    return new ModelAndView("GoodCategoriesSelect",
                            "models",
                            new HashMap<String, Object>(){{
                              put("checked", service.getGoodItemCategories(goodsItemId));
                              put("all", service.getGoodItemCategories(goodsItemId));
                            }}
                            );
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ModelAndView get(@PathVariable("id") Integer id)
  {
    GoodsCategory model = service.getGoodCategory(id);
    if (model != null)
      return new ModelAndView("GoodsCategoryEdit", "model", model);
    else
      return new ModelAndView("redirect:/category");
  }
  
  @RequestMapping(params = "new", method = RequestMethod.GET)
  public ModelAndView getEmptyEditForm()
  {
    return new ModelAndView("GoodsCategoryEdit", "model", service.getGoodCategoryEmpty());
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
  public String update(@ModelAttribute GoodsCategory goodCategory)
  {
    service.setGoodsCategory(goodCategory);
    return "redirect:/category";
  }
  
  @RequestMapping(method = RequestMethod.PUT)
  public ModelAndView putNew(GoodsCategory goodCategory)
  {
    try
    {
      service.setGoodsCategory(goodCategory);
      return new ModelAndView("redirect:/category");
    } catch (DuplicateKeyException Exception)
    {
      return new ModelAndView("GoodsCategoryEdit", "model", goodCategory);
    }
  }
  
  @RequestMapping(params = "new", method = RequestMethod.POST)
  public ModelAndView insertNew(GoodsCategory goodCategory)
  {
    return putNew(goodCategory);
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable("id") Integer id)
  {
    service.deleteGoodsCategory(id);
  }
  
}
