package com.intetics.lukyanenko.web

import com.intetics.lukyanenko.models.GoodsCategory
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

import javax.inject.Inject
import java.util.HashMap

@Controller
@RequestMapping(value = "/category")
class GoodsCategoryController @Inject
constructor(private val service: AppService) {

    @RequestMapping(value = *arrayOf("/", ""))
    fun listAll(): ModelAndView {
        return ModelAndView("GoodsCategoryList", "list", service.getGoodCategories())
    }

    @RequestMapping(params = arrayOf("ofitem"))
    fun listForGoodItem(@PathVariable(value = "ofitem") goodsItemId: Int): ModelAndView {
        return ModelAndView("GoodsCategoryList", "list_only", service.getGoodItemCategories(goodsItemId))
    }

    @RequestMapping(params = arrayOf("ofitem", "select"))
    fun listForGoodItem2(@PathVariable(value = "ofitem") goodsItemId: Int): ModelAndView {
        return ModelAndView("GoodCategoriesSelect",
                "models",
                mapOf("checked" to service.getGoodItemCategories(goodsItemId),
                        "all" to service.getGoodItemCategories(goodsItemId))
        )
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
    fun get(@PathVariable("id") id: Int): ModelAndView {
        val model = service.getGoodCategory(id)
        return if (model != null)
            ModelAndView("GoodsCategoryEdit", "model", model)
        else
            ModelAndView("redirect:/category")
    }

    @GetMapping(params = arrayOf("new"))
    fun emptyEditForm(): ModelAndView
            = ModelAndView("GoodsCategoryEdit", "model", service.getGoodCategoryEmpty())

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.POST))
    fun update(@ModelAttribute goodCategory: GoodsCategory): String {
        service.setGoodsCategory(goodCategory)
        return "redirect:/category"
    }

    @RequestMapping(method = arrayOf(RequestMethod.PUT))
    fun putNew(goodCategory: GoodsCategory): ModelAndView {
        try {
            service.setGoodsCategory(goodCategory)
            return ModelAndView("redirect:/category")
        } catch (Exception: DuplicateKeyException) {
            return ModelAndView("GoodsCategoryEdit", "model", goodCategory)
        }

    }

    @RequestMapping(params = arrayOf("new"), method = arrayOf(RequestMethod.POST))
    fun insertNew(goodCategory: GoodsCategory): ModelAndView {
        return putNew(goodCategory)
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.DELETE))
    fun delete(@PathVariable("id") id: Int) {
        service.deleteGoodsCategory(id)
    }

}
