package com.colptha.controllers;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.validators.interfaces.ShipmentFormValidator;
import com.colptha.services.ProductService;
import com.colptha.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
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

    @Secured({"ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping("/")
    public String root() {
        return "redirect:/shipment/all";
    }

    @Secured({"ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping(method = RequestMethod.POST)
    public String saveShipment(@Valid ShipmentForm shipmentForm, BindingResult bindingResult) {

        shipmentFormValidator.validate(shipmentForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "shipment/form";
        }

        try {
            shipmentService.saveOrUpdate(shipmentForm);
        } catch (Exception e) {
            return "shipment/inventory-error";
        }

        return "redirect:/shipment/all";
    }

    @Secured({"ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping("/all")
    public String listAll(Model model) {
        model.addAttribute("shipments", shipmentService.listAll());

        return "shipment/all";
    }

    @Secured({"ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping("/show/{id}")
    public String showOne(@PathVariable Integer id, Model model) {
        ShipmentForm shipmentForm = shipmentService.findOne(id);

        model.addAttribute("shipment", shipmentForm);
        model.addAttribute("lots", shipmentService.listProductLotsByProductId(shipmentForm));

        return "shipment/show";
    }

    @Secured({"ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping("/new")
    public String newShipment(Model model) {
        model.addAttribute("shipmentForm", new ShipmentForm());
        model.addAttribute("products", productService.listAll());

        return "shipment/form";
    }

    @Secured({"ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping("/edit/{id}")
    public String editShipment(@PathVariable Integer id, Model model) {
        ShipmentForm shipmentForm = shipmentService.findOne(id);
        TreeMap<Integer, ProductLot> lots = shipmentService.listProductLotsByProductId(shipmentForm);
        lots.forEach((integer, productLot) -> shipmentForm.getPossibleProductLots().get(integer).setQuantity(productLot.getQuantity()));

        model.addAttribute("shipmentForm", shipmentForm);
        model.addAttribute("lots", lots);

        return "shipment/form";
    }

    @Secured({"ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping("/delete/confirm/{id}")
    public String confirmDelete(@PathVariable Integer id, Model model) {
        model.addAttribute("shipmentId", id);

        return "shipment/delete";
    }

    @Secured({"ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteShipment(@PathVariable Integer id) {


        try {
            shipmentService.clearInventoryByShipmentId(id);
        } catch (Exception e) {
            return "shipment/inventory-error";
        }

        shipmentService.delete(id);
        return "redirect:/shipment/all";
    }
}
