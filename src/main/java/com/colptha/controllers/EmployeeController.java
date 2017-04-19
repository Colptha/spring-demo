package com.colptha.controllers;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.entities.Employee;
import com.colptha.dom.validators.EmployeeFormValidator;
import com.colptha.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void setEmployeeService(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setEmployeeFormValidator(final EmployeeFormValidator employeeFormValidator) {
        this.employeeFormValidator = employeeFormValidator;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String postEmployee(@Valid EmployeeForm employeeForm, BindingResult bindingResult) {
        employeeFormValidator.validate(employeeForm, bindingResult);

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            return "employee/form";
        }

        employeeService.saveOrUpdate(employeeForm);

        return "redirect:/employee/all";
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/employee/all";
    }

    @RequestMapping("/all")
    public String listAll(Model model) {
        model.addAttribute("employees", new TreeMap<>(employeeService.listAll()));

        return "employee/all";
    }

    @RequestMapping("/show/{id}")
    public String showOne(@PathVariable String id, Model model) {
        model.addAttribute("employee", employeeService.findOne(id));

        return "employee/show";
    }

    @RequestMapping("/new")
    public String newEmployee(Model model) {
        model.addAttribute("employeeForm", new Employee());

        return "employee/form";
    }

    @RequestMapping("/edit/{id}")
    public String editEmployee(@PathVariable String id, Model model) {
        model.addAttribute("employeeForm", employeeService.findOne(id));

        return "employee/form";
    }

}
