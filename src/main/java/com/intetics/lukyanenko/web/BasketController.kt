package com.intetics.lukyanenko.web

import com.intetics.lukyanenko.models.OrderDetail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.servlet.ModelAndView
import java.util.*
import java.util.regex.Pattern

@Controller
@RequestMapping(value = "/basket")
class BasketController @Autowired
constructor(private val service: AppService) {

    @PostMapping(value = *arrayOf("/", ""))
    fun update(@RequestParam params: Map<String, String>): ModelAndView {
        val rowChangeRequests = ArrayList<OrderDetail>()
        val pattern = Pattern.compile("row_(\\d+)")
        for ((key, value) in params) {
            pattern.matcher(key).takeIf { it.find() }?.let { rowId ->
                rowChangeRequests.add(service.getOrderDetailEmpty().apply {
                    id = Integer.valueOf(rowId.group(1))
                    quantity = value.toDouble()
                })
            }
        }
        val basket = service.getBasket(sessionId)
        service.updateBasket(basket, rowChangeRequests)
        return ModelAndView("Basket", "basket", basket)
    }

    @GetMapping(value = *arrayOf("/", ""))
    fun get(): ModelAndView =
            ModelAndView("Basket", "basket", service.getBasket(sessionId, true))

    @GetMapping(value = "/short")
    fun short(): ModelAndView =
            ModelAndView("BasketInfo", "basket", service.getBasket(sessionId, true))

    @PostMapping(value = "/checkout")
    fun checkout(): String =
        try {
            service.checkIfBasketCanBeCheckedOut(service.getBasket(sessionId))
            "redirect:/basket/confirm-check-out"
        } catch (E: AppService.CustomerIsAnonymous) {
            "redirect:/customer?new&from_basket"
        } catch (E: IllegalArgumentException) {
            "redirect:/InvalidRequest" //todo check if it is correct instruction
        }

    @GetMapping(value = "confirm-check-out")
    fun viewCheckoutConfirmation(): ModelAndView =
            ModelAndView("BasketConfirmCheckout")

    @PostMapping(value = "confirm-check-out")
    fun confirmCheckout(): String =
            try {
                val newOrder = service.makeOrderFromBasket(service.getBasket(sessionId))
                String.format("redirect:/order/%d?congrats", newOrder.id)
            } catch (E: Throwable) {
                "redirect:/InvalidRequest"
            }

    private val sessionId: String
        get() = RequestContextHolder.currentRequestAttributes().sessionId

    @DeleteMapping(value = "/{id}")
    fun deleteItem(@PathVariable("id") goodsItemId: Int?) {
        update(mapOf("row_$goodsItemId" to "0"))
    }

    @DeleteMapping(value = *arrayOf("", "/"))
    fun clear(): ModelAndView =
        sessionId.let {
            service.clearBasket(it)
            ModelAndView("Basket", "basket", service.getBasket(it))
        }
}
