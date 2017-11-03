package com.intetics.lukyanenko.web

import com.intetics.lukyanenko.models.Customer
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

import javax.inject.Inject

@Controller
@RequestMapping("/customer")
class CustomerController @Inject
constructor(private val service: AppService) {

    @RequestMapping(value = *arrayOf("/", ""), method = arrayOf(RequestMethod.GET))
    fun list(): ModelAndView = ModelAndView("CustomerList", "list", service.getCustomerList())

    @RequestMapping(value = "/{id}/edit", method = arrayOf(RequestMethod.GET))
    fun getEdit(@PathVariable("id") id: Int): ModelAndView {
        return ModelAndView("CustomerEdit", "model", service.getCustomer(id))
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.POST))
    fun update(@ModelAttribute customer: Customer): String {
        service.setCustomer(customer)
        return String.format("redirect:/customer/%d", customer.id)
    }

    @RequestMapping(params = arrayOf("new"), method = arrayOf(RequestMethod.GET))
    fun emptyEditForm(): ModelAndView = ModelAndView("CustomerEdit", "model", Customer())

    @RequestMapping(method = arrayOf(RequestMethod.PUT))
    fun putNew(customer: Customer): ModelAndView {
        try {
            service.setCustomer(customer)
            return ModelAndView("redirect:/customer")
        } catch (Exception: DuplicateKeyException) {
            return ModelAndView("CustomerEdit", "model", customer)
        }
    }

    @RequestMapping(params = arrayOf("new"), method = arrayOf(RequestMethod.POST))
    fun insertNew(customer: Customer): ModelAndView {
        return putNew(customer)
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.DELETE))
    fun delete(@PathVariable("id") id: Int) {
        service.deleteCustomer(id)
    }
}