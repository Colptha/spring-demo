package com.colptha.controllers;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

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

    @RequestMapping(method = RequestMethod.POST)
    public String saveShipment(@Valid ShipmentForm shipmentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "shipment/form";
        }

        shipmentService.saveOrUpdate(shipmentForm);

        return "redirect:/shipment/all";
    }

    @RequestMapping("/all")
    public String listAll(Model model) {
        model.addAttribute("shipments", shipmentService.listAll());

        return "shipment/all";
    }

    @RequestMapping("/show/{id}")
    public String showOne(@PathVariable Integer id, Model model) {
        ShipmentForm shipmentForm = shipmentService.findOne(id);

        model.addAttribute("shipment", shipmentForm);
        model.addAttribute("lots", shipmentService.listProductLotsByProductId(shipmentForm));

        return "shipment/show";
    }

    @RequestMapping("/new")
    public String newShipment(Model model) {
        model.addAttribute("shipmentForm", new ShipmentForm());

        return "shipment/form";
    }

    @RequestMapping("/edit/{id}")
    public String editShipment(@PathVariable Integer id, Model model) {
        model.addAttribute("shipmentForm", shipmentService.findOne(id));

        return "shipment/form";
    }
}
