package com.colptha.services;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.entities.Employee;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Colptha on 3/31/17.
 */
public interface EmployeeService {
    Map<String, EmployeeForm> listAll();

    EmployeeForm findByEIN(String employeeCode) throws NoSuchElementException;

    EmployeeForm saveOrUpdate(EmployeeForm employeeForm);

    EmployeeForm delete(String employeeCode);
}
