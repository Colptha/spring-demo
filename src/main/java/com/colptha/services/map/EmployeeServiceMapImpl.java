package com.colptha.services.map;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.converters.EmployeeConverter;
import com.colptha.dom.entities.Employee;
import com.colptha.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by Colptha on 3/31/17.
 */
@Service
@Profile("map") // could have just used employee instead of employeeform but wanted to use the interface for loose coupling, and the interface defines these return types
public class EmployeeServiceMapImpl implements EmployeeService {

    private Map<String, Employee> employees = new HashMap<>();
    private EmployeeConverter employeeConverter;

    @Autowired
    public void setEmployeeConverter(EmployeeConverter employeeConverter) {
        this.employeeConverter = employeeConverter;
    }

    @Override
    public Map<String, EmployeeForm> listAll() {
        Map<String, EmployeeForm> employeeFormMap = new HashMap<>();
        employees.forEach(
                (employeeId, employee) -> employeeFormMap.put(employeeId, employeeConverter.convert(employee)));

        return employeeFormMap;
    }

    @Override
    public EmployeeForm findOne(String employeeCode) throws NoSuchElementException {
        return employeeConverter.convert(employees.get(employeeCode));
    }

    @Override
    public EmployeeForm saveOrUpdate(EmployeeForm employeeForm) {
        // dates will be trashed during conversion (for consistency of created on dates)
        Employee employee = employeeConverter.convert(employeeForm);
        String employeeId = employee.getEmployeeId();

        // check for and add creation date
        if (employees.containsKey(employeeId)) {
            employee.setCreatedOn(employees.get(employeeId).getCreatedOn());
        }

        // in database backed services hibernate will do this, have to manually implement here
        employee.updateTimeStamps();
        employees.put(employeeId, employee);

        // send back with valid timestamps
        return employeeConverter.convert(employee);
    }

    @Override
    public void delete(String employeeCode) {
        employeeConverter.convert(employees.remove(employeeCode));
    }

}
