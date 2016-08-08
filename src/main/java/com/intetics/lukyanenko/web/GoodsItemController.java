package com.intetics.lukyanenko.web;

import com.intetics.lukyanenko.models.GoodsCategory;
import com.intetics.lukyanenko.models.GoodsItem;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.ArrayList;
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
  
  @RequestMapping(value = "/{id}", params = "basket", method = RequestMethod.POST)
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
  public String update(@ModelAttribute GoodsItem goodsItem,
                       BindingResult bindingResult,
                       @RequestParam Map<String, String> params)
  {
    try
    {
      parseParamsToCategories(params, goodsItem);
    }
    catch (Throwable E)
    {
      bindingResult.addError(new ObjectError("goodsItem.categories", E.getMessage()));
    }
    service.setGoodsItem(goodsItem);
    return "redirect:/goods";
  }
  
  void parseParamsToCategories(Map<String, String> source, GoodsItem goodsItem)
  {
    if (goodsItem.getCategories() == null)
      goodsItem.setCategories(new ArrayList<>());
    //
    for(Map.Entry<String, String> item : source.entrySet())
      if (item.getKey().contains("categories/"))
      {
        GoodsCategory obj = service.getGoodCategoryEmpty();
        obj.setId(Integer.parseInt(item.getValue()));
        goodsItem.getCategories().add(obj);
      }
  }
  
  @RequestMapping(method = RequestMethod.PUT)
  public ModelAndView putNew(GoodsItem goodsItem,
                             BindingResult bindingResult)
  {
    if (bindingResult.hasErrors())
    {
      ModelAndView mv = new ModelAndView("GoodsItemEdit");
      mv.addObject("model", goodsItem);
      mv.addObject("categories", service.getGoodCategories());
      return mv;
    } else
      try
      {
        service.setGoodsItem(goodsItem);
        return new ModelAndView("redirect:/goods");
      }
      catch (DuplicateKeyException Exception)
      {
        ModelAndView mv = new ModelAndView("GoodsItemEdit");
        mv.addObject("model", goodsItem);
        mv.addObject("categories", service.getGoodCategories());
        return mv;
      }
  }
  
  @RequestMapping(params = "new", method = RequestMethod.POST)
  public ModelAndView insertNew(@ModelAttribute GoodsItem goodsItem,
                                BindingResult bindingResult,
                                @RequestParam Map<String, String> params)
  {
    try
    {
      parseParamsToCategories(params, goodsItem);
    }
    catch (Throwable E)
    {
      bindingResult.addError(new ObjectError("goodsItem.categories", E.getMessage()));
    }
    return putNew(goodsItem, bindingResult);
  }
  
  
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable("id") Integer id)
  {
    service.deleteGoodsItem(id);
  }
  
}
