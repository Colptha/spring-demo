package com.colptha.controllers;

import com.colptha.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Colptha on 3/31/17.
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/employee/all";
    }

    @RequestMapping("/all")
    public String listAll(Model model) {
        model.addAttribute("employees", employeeService.listAll());

        return "employee/all";
    }

    @RequestMapping("/show/{id}")
    public String showOne(@PathVariable String id, Model model) {
        model.addAttribute("employee", employeeService.findOne(id));

        return "employee/show";
    }
}
