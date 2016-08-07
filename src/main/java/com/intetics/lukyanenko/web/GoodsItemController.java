package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.GoodsCategory;
import com.intetics.lukyanenko.models.GoodsItem;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.List;
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
  
  private Double parseDoubleNullable(String value)
  {
    if (value == null || value.isEmpty())
      return null;
    else
      try
      {
        return Double.parseDouble(value);
      }
      catch
        (Throwable E) { return null; }
  }
  
  @RequestMapping(value = "", params = {"price_from", "price_till", "category", "item_name"})
  public ModelAndView search(@RequestParam Map<String, String> searchParams)
  {
    return new ModelAndView("GoodsItemList",
                            "list",
                            service.searchGoodItems(
                              parseDoubleNullable(searchParams.get("price_from")),
                              parseDoubleNullable(searchParams.get("price_till")),
                              searchParams.get("category"),
                              searchParams.get("item_name")
                            ));
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ModelAndView get(@PathVariable("id") Integer id)
  {
    GoodsItem model = service.getGoodsItem(id);
    if (model != null)
      return new ModelAndView("GoodsItemShow", "model", model);
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
    GoodsItem model = service.getGoodsItemForEdit(id);
    if (model != null) {
      ModelAndView mv = new ModelAndView("GoodsItemEdit");
      mv.addObject("model", model);
      mv.addObject("categories", service.getGoodCategories());
      return mv;
    }
    else
      return new ModelAndView("redirect:/goods");
  }
  @RequestMapping(params = "new", method = RequestMethod.GET)
  public ModelAndView getEmptyEditForm()
  {
    ModelAndView mv = new ModelAndView("GoodsItemEdit");
    mv.addObject("model", service.getEmptyGoodsItem());
    mv.addObject("categories", service.getGoodCategories());
    return mv;
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
  public String update(@ModelAttribute GoodsItem goodsItem)
  {
    service.setGoodsItem(goodsItem);
    return "redirect:/goods";
  }
  
  @RequestMapping(method = RequestMethod.PUT)
  public ModelAndView putNew(GoodsItem goodsItem, Map<String, String> params)
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
  
  @RequestMapping(params = "new", method = RequestMethod.POST)
  public ModelAndView insertNew(@ModelAttribute GoodsItem goodsItem, BindingResult result,
  @RequestParam Map<String, String> params)
  {
    
    return putNew(goodsItem, params);
  }
  
  
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable("id") Integer id)
  {
    service.deleteGoodsItem(id);
  }
  
}
