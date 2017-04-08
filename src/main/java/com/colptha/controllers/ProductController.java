package com.colptha.controllers;

import com.colptha.dom.enums.ProductId;
import com.colptha.services.ProductService;
import com.colptha.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.TreeMap;

/**
 * Created by Colptha on 3/31/17.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    private ShipmentService shipmentService;

    @Autowired
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Autowired
    public void setProductService(ProductService productService) { this.productService = productService; }

    @RequestMapping("/")
    public String root() {
        return "redirect:/product/all";
    }

    @RequestMapping("/all")
    public String listAll(Model model) {
        model.addAttribute("products", new TreeMap<>(productService.listAll()));
        return "product/all";
    }

    @RequestMapping("/show/{id}")
    public String showOne(@PathVariable ProductId id, Model model) {
        model.addAttribute("product", productService.findOne(id));
        model.addAttribute("lots", shipmentService.listByProductId(id));

        return "product/show";
    }
}
