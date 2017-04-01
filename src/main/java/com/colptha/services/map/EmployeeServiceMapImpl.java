package com.colptha.services.map;

import com.colptha.dom.entities.Employee;
import com.colptha.services.EmployeeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by Colptha on 3/31/17.
 */
@Service
@Profile("map")
public class EmployeeServiceMapImpl implements EmployeeService {
    private Map<String, Employee> employees = new HashMap<>();

    public Map<String, Employee> listAll() {
        return employees;
    }

    public Employee findByEIN(String employeeCode) throws NoSuchElementException {
        return employees.get(employeeCode);
    }

    public Employee saveOrUpdate(Employee employee) {
        return employees.put(employee.getEmployeeCode(), employee);
    }

    public Employee delete(String employeeCode) {
        return employees.remove(employeeCode);
    }

}
