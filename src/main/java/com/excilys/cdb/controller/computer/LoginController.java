package com.excilys.cdb.controller.computer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * login page controller.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
    public ModelAndView getPage() {
        return new ModelAndView("login");
    }
}
