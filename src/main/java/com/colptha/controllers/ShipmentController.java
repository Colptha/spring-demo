package com.colptha.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Colptha on 4/1/17.
 */
@Controller
@RequestMapping("/shipment")
public class ShipmentController {

    @RequestMapping("/")
    public String root() {
        return "redirect:/shipment/all";
    }

    @RequestMapping("/all")
    public String listAll() {
        return "shipment/all";
    }

}
