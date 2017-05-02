package com.colptha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Colptha on 3/31/17.
 */
@Controller
public class IndexController {

    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String loginForm(@RequestParam(name = "error", required = false) String error, Model model) {
        model.addAttribute("error", error != null);
        return "login";
    }

    @RequestMapping("/access_denied")
    public String denied() {
        return "access_denied";
    }

    @RequestMapping("/logout_success")
    public String logout() {
        return "logout_success";
    }

}
