package com.colptha.services;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.entities.Employee;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.NoSuchElementException;

/**
 * Created by Colptha on 3/31/17.
 */
public interface EmployeeService extends CRUDService<EmployeeForm, String>{
    boolean isIdInUse(EmployeeForm employeeForm);
    UserDetails findUserDetails(String query) throws NoSuchElementException;
    void resetPassword(String id);
}
