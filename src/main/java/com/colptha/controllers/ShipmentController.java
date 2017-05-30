package com.colptha.controllers;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.entities.exceptions.NegativeInventoryException;
import com.colptha.dom.validators.interfaces.ShipmentFormValidator;
import com.colptha.services.ProductService;
import com.colptha.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Created by Colptha on 4/7/17.
 */
@Controller
@RequestMapping("/shipment")
public class ShipmentController {

    private ShipmentService shipmentService;
    private ProductService productService;
    private ShipmentFormValidator shipmentFormValidator;

    @Autowired
    public void setShipmentFormValidator(ShipmentFormValidator shipmentFormValidator) {
        this.shipmentFormValidator = shipmentFormValidator;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setShipmentService(ShipmentService shipmentService) { this.shipmentService = shipmentService; }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/")
    public String root() {
        return "redirect:/shipment/all";
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @PostMapping
    public String saveShipment(ShipmentForm shipmentForm, BindingResult bindingResult) {

        shipmentFormValidator.validate(shipmentForm, bindingResult);

        if (bindingResult.hasErrors()) {
            shipmentForm.setUpdatedOn(shipmentService.findOne(shipmentForm.getShipmentId()).getUpdatedOn());
            return "shipment/form";
        }

        try {
            shipmentService.saveOrUpdate(shipmentForm);
        } catch (NegativeInventoryException e) {
            return "shipment/inventory-error";
        }

        return "redirect:/shipment/all";
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/all")
    public String listAll(Model model) {
        model.addAttribute("shipments", shipmentService.listAll());

        return "shipment/all";
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/show/{id}")
    public String showOne(@PathVariable Integer id, Model model) {
        try {
            ShipmentForm shipmentForm = shipmentService.findOne(id);
            model.addAttribute("shipment", shipmentForm);
            model.addAttribute("lots", shipmentService.listProductLotsByProductId(shipmentForm));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return "redirect:/shipment/all";
        }

        return "shipment/show";
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/new")
    public String newShipment(Model model) {
        model.addAttribute("shipmentForm", new ShipmentForm());
        model.addAttribute("products", productService.listAll());

        return "shipment/form";
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/edit/{id}")
    public String editShipment(@PathVariable Integer id, Model model) {
        try {
            ShipmentForm shipmentForm = shipmentService.findOne(id);

            TreeMap<Integer, ProductLot> lots = shipmentService.listProductLotsByProductId(shipmentForm);
            lots.forEach((integer, productLot) -> shipmentForm.getPossibleProductLots().get(integer).setQuantity(productLot.getQuantity()));

            model.addAttribute("shipmentForm", shipmentForm);
            model.addAttribute("lots", lots);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return "redirect:/shipment/all";
        }

        return "shipment/form";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/delete/confirm/{id}")
    public String confirmDelete(@PathVariable Integer id, Model model) {
        model.addAttribute("shipmentId", id);

        return "shipment/delete";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping(value = "/delete/{id}")
    public String deleteShipment(@PathVariable Integer id) {

        try {
            shipmentService.clearInventoryByShipmentId(id);
        } catch (NegativeInventoryException e) {
            return "shipment/inventory-error";
        }

        shipmentService.delete(id);
        return "redirect:/shipment/all";
    }
}
