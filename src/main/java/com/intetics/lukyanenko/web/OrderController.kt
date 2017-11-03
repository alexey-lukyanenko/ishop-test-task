package com.intetics.lukyanenko.web

import com.intetics.lukyanenko.models.Customer
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

import javax.inject.Inject

@Controller
@RequestMapping(value = "/order")
class OrderController @Inject
constructor(private val service: AppService) {

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
    fun get(@RequestParam id: Int): ModelAndView {
        return ModelAndView("OrderEdit", "model", service.getOrder(id))
    }

    @RequestMapping(value = "/{id}/cancel", method = arrayOf(RequestMethod.POST))
    fun cancel(@PathVariable("id") id: Int): String {
        service.deleteOrder(id)
        return "redirect:/order"
    }

    @RequestMapping(value = *arrayOf("/{id}", ""), method = arrayOf(RequestMethod.POST))
    fun update(@PathVariable("id") orderId: Int,
               @RequestParam params: Map<String, String>): ModelAndView {
        service.updateOrder(orderId, params)
        return ModelAndView("OrderEdit", "model", service.getOrder(orderId))
    }

    // todo Get customer from security context
    @RequestMapping(value = *arrayOf("/", ""), method = arrayOf(RequestMethod.GET))
    fun list(): ModelAndView {
        return ModelAndView("OrderList" /*, "model", service.getOrderList(customer)*/)
    }

    @RequestMapping(value = "/{orderId}/deleteItem/{itemId}")
    fun deleteItem(@PathVariable("orderId") orderId: Int,
                   @PathVariable("ItemId") itemId: Int) {
        service.updateOrder(orderId, mapOf(itemId.toString() to "0"))
    }
}
