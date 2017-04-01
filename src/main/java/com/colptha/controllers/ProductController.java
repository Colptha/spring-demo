package com.colptha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Colptha on 3/31/17.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @RequestMapping("/")
    public String root() {
        return "redirect:/product/all";
    }

    @RequestMapping("/all")
    public String listAll() {
        return "product/all";
    }
}
