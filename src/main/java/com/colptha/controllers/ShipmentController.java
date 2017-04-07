package com.colptha.controllers;

import com.colptha.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Colptha on 4/7/17.
 */
@Controller
@RequestMapping("/shipment")
public class ShipmentController {

    private ShipmentService shipmentService;

    @Autowired
    public void setShipmentService(ShipmentService shipmentService) { this.shipmentService = shipmentService; }

    @RequestMapping("/")
    public String root() {
        return "redirect:/shipment/all";
    }

    @RequestMapping("/all")
    public String listAll(Model model) {
        model.addAttribute("shipments", shipmentService.listAll());

        return "shipment/all";
    }

    @RequestMapping("/show/{id}")
    public String showOne(@PathVariable Integer id, Model model) {
        model.addAttribute("shipment", shipmentService.findOne(id));

        return "shipment/show";
    }
}
