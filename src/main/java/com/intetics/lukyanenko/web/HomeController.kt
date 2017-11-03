package com.intetics.lukyanenko.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class HomeController {
    val defaultPage: ModelAndView
        @RequestMapping("/")
        get() = ModelAndView("DefaultPage")

    val invalidRequest: ModelAndView
        @GetMapping("InvalidRequest")
        get() = ModelAndView("InvalidRequest")
}

