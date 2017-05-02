package com.colptha.controllers;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.command.PasswordForm;
import com.colptha.dom.validators.interfaces.EmployeeFormValidator;
import com.colptha.dom.validators.interfaces.PasswordFormValidator;
import com.colptha.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Colptha on 4/27/17.
 */
@Controller
@RequestMapping("/account")
public class AccountController {
    private EmployeeService employeeService;
    private EmployeeFormValidator employeeFormValidator;
    private PasswordFormValidator passwordFormValidator;

    @Autowired
    public void setPasswordFormValidator(PasswordFormValidator passwordFormValidator) {
        this.passwordFormValidator = passwordFormValidator;
    }

    @Autowired
    public void setEmployeeFormValidator(EmployeeFormValidator employeeFormValidator) {
        this.employeeFormValidator = employeeFormValidator;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Secured({"ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping(path = {"/", "/show"}, method = RequestMethod.GET)
    public String showAccount(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("employee", employeeService.findOne(userDetails.getUsername()));
        return "account/show";
    }

    @Secured({"ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String editAccount(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        EmployeeForm employeeForm =  employeeService.findOne(userDetails.getUsername());
        model.addAttribute("employeeForm", employeeForm);

        return "account/edit";
    }

    @Secured({"ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public String updateAccount(@Valid EmployeeForm employeeForm, BindingResult bindingResult) {

        employeeFormValidator.validate(employeeForm, bindingResult);

        if (bindingResult.hasErrors()) {
            employeeForm.setUpdatedOn(employeeService.findOne(employeeForm.getEmployeeId()).getUpdatedOn());
            return "account/edit";
        }

        try {
            employeeService.saveOrUpdate(employeeForm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/account/show";
    }

    @Secured({"ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping(path = "/change_password", method = RequestMethod.GET)
    public String changePasswordPage(Model model) {
        model.addAttribute("passwordForm", new PasswordForm());

        return"account/change_password";
    }

    @Secured({"ROLE_USER","ROLE_MANAGER","ROLE_ADMIN"})
    @RequestMapping(path = "/change_password", method = RequestMethod.POST)
    public String changePassword(@AuthenticationPrincipal UserDetails userDetails,
                                 @Valid PasswordForm passwordForm,
                                 BindingResult bindingResult) {
        passwordFormValidator.validate(passwordForm, bindingResult);
        passwordFormValidator.checkCurrentPassword(passwordForm, bindingResult, userDetails);
        if (bindingResult.hasErrors()) {
            return "account/change_password";
        }

        employeeService.changePassword(passwordForm, userDetails);
        return "redirect:/account/show";
    }
}
