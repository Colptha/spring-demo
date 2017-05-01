package com.colptha.controllers;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.validators.EmployeeFormValidator;
import com.colptha.services.EmployeeService;
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
 * Created by Colptha on 3/31/17.
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeFormValidator employeeFormValidator;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setEmployeeFormValidator(EmployeeFormValidator employeeFormValidator) {
        this.employeeFormValidator = employeeFormValidator;
    }

    @Secured({"ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping(method = RequestMethod.POST)
    public String postEmployee(@Valid EmployeeForm employeeForm, BindingResult bindingResult) {

        employeeFormValidator.validate(employeeForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "employee/form";
        }

        try {
            employeeService.saveOrUpdate(employeeForm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/employee/all";
    }

    @Secured({"ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping("/")
    public String root() {
        return "redirect:/employee/all";
    }

    @Secured({"ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping("/all")
    public String listAll(Model model) {
        model.addAttribute("employees", new TreeMap<>(employeeService.listAll()));

        return "employee/all";
    }

    @Secured({"ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping("/show/{id}")
    public String showOne(@PathVariable String id, Model model) {
        model.addAttribute("employee", employeeService.findOne(id));

        return "employee/show";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/new")
    public String newEmployee(Model model) {
        model.addAttribute("employeeForm", new EmployeeForm());

        return "employee/form";
    }

    @Secured({"ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping("/edit/{id}")
    public String editEmployee(@PathVariable String id, Model model) {
        model.addAttribute("employeeForm", employeeService.findOne(id));

        return "employee/form";
    }

    @Secured({"ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping("/password_reset/{id}")
    public String resetPassword(@PathVariable String id) {
        employeeService.resetPassword(id);

        return "redirect:/employee/all";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/remove/{id}")
    public String removeEmployee(@PathVariable String id) {
        employeeService.delete(id);

        return "redirect:/employee/all";
    }

}
