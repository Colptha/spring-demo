package com.colptha.services;

import com.colptha.dom.entities.Employee;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Colptha on 3/31/17.
 */
public interface EmployeeService {
    public Map<String, Employee> listAll();

    public Employee findByEIN(String employeeCode) throws NoSuchElementException;

    public Employee saveOrUpdate(Employee employee);

    public Employee delete(String employeeCode);
}
