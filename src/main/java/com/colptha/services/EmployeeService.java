package com.colptha.services;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.command.PasswordForm;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.NoSuchElementException;

/**
 * Created by Colptha on 3/31/17.
 */
public interface EmployeeService extends CRUDService<EmployeeForm, String>{
    boolean isIdInUse(EmployeeForm employeeForm);
    UserDetails findUserDetails(String query) throws NoSuchElementException;

    @Secured({"ROLE_MANAGER","ROLE_ADMIN"})
    void resetPassword(String id);

    @PreAuthorize("isFullyAuthenticated()")
    void changePassword(PasswordForm passwordForm, UserDetails userDetails);
}
