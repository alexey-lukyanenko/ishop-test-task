package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.GoodsItem;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.Map;

@Controller
@RequestMapping(value = "/goods")
public class GoodsItemController
{
  private final Service service;
  
  @Inject
  public GoodsItemController(Service service)
  {
    this.service = service;
  }
  
  @RequestMapping(value = {"/", ""})
  public ModelAndView getSearch()
  {
    return new ModelAndView("GoodsSearch");
  }
  
  @RequestMapping(value = {"/search", "/search/"})
  public ModelAndView search(@RequestParam Map<String, String> searchParams)
  {
    return new ModelAndView("GoodsItemList", "list", service.searchGoodItems(searchParams));
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ModelAndView get(@PathVariable("id") Integer id)
  {
    GoodsItem model = service.getGoodsItem(id);
    if (model != null)
      return new ModelAndView("GoodItemShowAndSelect", "model", model);
    else
      return new ModelAndView("redirect:/goods");
  }
  
  @RequestMapping(value = "/{id}/basket", method = RequestMethod.POST)
  public void addToBasket(@PathVariable("id") Integer id)
  {
    service.addGoodsItemToBasket(id);
  }
  
  @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
  public ModelAndView getEditView(@PathVariable("id") Integer id)
  {
    GoodsItem model = service.getGoodsItem(id);
    if (model != null)
      return new ModelAndView("GoodItemEdit", "model", model);
    else
      return new ModelAndView("redirect:/goods");
  }
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public ModelAndView getEmptyEditForm()
  {
    return new ModelAndView("GoodsItemEdit", "model", new GoodsItem());
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
  public String update(@ModelAttribute GoodsItem goodsItem)
  {
    service.setGoodsItem(goodsItem);
    return "redirect:/categories";
  }
  
  @RequestMapping(method = RequestMethod.PUT)
  public ModelAndView putNew(GoodsItem goodsItem)
  {
    try
    {
      service.setGoodsItem(goodsItem);
      return new ModelAndView("redirect:/goods");
    } catch (DuplicateKeyException Exception)
    {
      return new ModelAndView("GoodsItemEdit", "model", goodsItem);
    }
  }
  
  @RequestMapping(value = "/new", method = RequestMethod.POST)
  public ModelAndView insertNew(GoodsItem goodsItem)
  {
    return putNew(goodsItem);
  }
  
  
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable("id") Integer id)
  {
    service.deleteGoodsItem(id);
  }
  
}
