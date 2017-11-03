package com.intetics.lukyanenko.web

import com.intetics.lukyanenko.models.AppUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping(value = "/users")
class UserController @Autowired
constructor(protected val service: AppService) {

    @GetMapping(value = *arrayOf("", "/"))
    fun list(): ModelAndView = ModelAndView("UserList", "list", service.getAppUserList())

    @GetMapping(value = "/{name}")
    fun get(@PathVariable("name") name: String): ModelAndView {
        val model = service.getAppUserInfo(name)
        return if (model != null)
            ModelAndView("UserEdit", "model", model)
        else
            ModelAndView("redirect:/users")
    }

    @GetMapping(params = arrayOf("new"))
    fun emptyEditForm(): ModelAndView = ModelAndView("UserEdit", "model", service.getAppUserEmptyNew())

    @PostMapping(value = "/{name}")
    fun update(@ModelAttribute appUser: AppUser): String {
        appUser.isNew = false
        service.setAppUser(appUser)
        return "redirect:/users"
    }

    @PutMapping
    fun putNew(appUser: AppUser): ModelAndView {
        try {
            appUser.isNew = true
            service.setAppUser(appUser)
            return ModelAndView("redirect:/users")
        } catch (Exception: DuplicateKeyException) {
            return ModelAndView("UserEdit", "model", appUser)
        }
    }

    @PostMapping(params = arrayOf("new"))
    fun insertNew(appUser: AppUser): ModelAndView = putNew(appUser)

    @DeleteMapping(value = "/{name}")
    fun delete(@PathVariable("name") name: String) {
        service.deleteAppUser(name)
    }
}
