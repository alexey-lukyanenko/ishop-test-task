package com.intetics.lukyanenko.web

import com.intetics.lukyanenko.models.GoodsItem
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.servlet.ModelAndView

import javax.inject.Inject

@Controller
@RequestMapping(value = "/goods")
class GoodsItemController @Inject
constructor(private val service: AppService) {

    @RequestMapping(value = *arrayOf("/", ""))
    fun search(): ModelAndView = ModelAndView("GoodsSearch")

    private fun parseDoubleNullable(value: String?): Double? {
        return value?.let {
            try {
                java.lang.Double.parseDouble(it)
            } catch (E: Throwable) {
                null
            }
        }
    }

    @RequestMapping(value = "", params = arrayOf("price_from", "price_till", "category", "item_name"))
    fun search(@RequestParam searchParams: Map<String, String>): ModelAndView {
        return ModelAndView("GoodsItemList",
                "list",
                service.searchGoodItems(
                        parseDoubleNullable(searchParams["price_from"]),
                        parseDoubleNullable(searchParams["price_till"]),
                        searchParams["category"],
                        searchParams["item_name"]
                ))
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
    fun get(@PathVariable("id") id: Int): ModelAndView {
        val model = service.getGoodsItem(id)
        return if (model != null)
            ModelAndView("GoodsItemShow", "model", model)
        else
            ModelAndView("redirect:/goods")
    }

    @RequestMapping(value = "/{id}", params = arrayOf("basket"), method = arrayOf(RequestMethod.POST))
    fun addToBasket(@PathVariable("id") id: Int) {
        service.addGoodsItemToBasket(id,
                RequestContextHolder.currentRequestAttributes().sessionId)
    }

    @RequestMapping(value = "/{id}/edit", method = arrayOf(RequestMethod.GET))
    fun getEditView(@PathVariable("id") id: Int): ModelAndView {
        val model = service.getGoodsItemForEdit(id)
        if (model != null) {
            val mv = ModelAndView("GoodsItemEdit")
            mv.addObject("model", model)
            mv.addObject("categories", service.getGoodCategories())
            return mv
        } else
            return ModelAndView("redirect:/goods")
    }

    @RequestMapping(params = arrayOf("new"), method = arrayOf(RequestMethod.GET))
    fun emptyEditForm(): ModelAndView =
            ModelAndView("GoodsItemEdit").apply {
                addObject("model", service.getEmptyGoodsItem())
                addObject("categories", service.getGoodCategories())
            }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.POST))
    fun update(@ModelAttribute goodsItem: GoodsItem,
               bindingResult: BindingResult,
               @RequestParam params: Map<String, String>): String {
        try {
            parseParamsToCategories(params, goodsItem)
        } catch (E: Throwable) {
            bindingResult.addError(ObjectError("goodsItem.categories", E.message))
        }

        service.setGoodsItem(goodsItem)
        return "redirect:/goods"
    }

    private fun parseParamsToCategories(source: Map<String, String>, goodsItem: GoodsItem) {
        for ((key, value) in source)
            if (key.contains("categories/")) {
                goodsItem.categories.add(service.getGoodCategoryEmpty().apply { id = Integer.parseInt(value) })
            }
    }

    @RequestMapping(method = arrayOf(RequestMethod.PUT))
    fun putNew(goodsItem: GoodsItem,
               bindingResult: BindingResult): ModelAndView {
        fun errorView(): ModelAndView =
                ModelAndView("GoodsItemEdit").apply {
                    addObject("model", goodsItem)
                    addObject("categories", service.getGoodCategories())
                }
        return if (bindingResult.hasErrors()) {
            errorView()
        } else {
            try {
                service.setGoodsItem(goodsItem)
                ModelAndView("redirect:/goods")
            } catch (Exception: DuplicateKeyException) {
                errorView()
            }
        }
    }

    @RequestMapping(params = arrayOf("new"), method = arrayOf(RequestMethod.POST))
    fun insertNew(@ModelAttribute goodsItem: GoodsItem,
                  bindingResult: BindingResult,
                  @RequestParam params: Map<String, String>): ModelAndView {
        try {
            parseParamsToCategories(params, goodsItem)
        } catch (E: Throwable) {
            bindingResult.addError(ObjectError("goodsItem.categories", E.message))
        }
        return putNew(goodsItem, bindingResult)
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.DELETE))
    fun delete(@PathVariable("id") id: Int) {
        service.deleteGoodsItem(id)
    }
}