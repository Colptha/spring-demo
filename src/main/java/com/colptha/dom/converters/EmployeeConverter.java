package com.colptha.dom.converters;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.entities.Employee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by Colptha on 4/1/17.
 */
@Component
public class EmployeeConverter implements Converter<Employee, EmployeeForm> {

    @Override
    public EmployeeForm convert(Employee employee) {
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setLastName(employee.getLastName());
        employeeForm.setFirstName(employee.getFirstName());
        employeeForm.setEmployeeId(employee.getEmployeeId());
        employeeForm.setCreatedOn(employee.getCreatedOn());
        employeeForm.setUpdatedOn(employee.getUpdatedOn());
        employeeForm.setVersion(employee.getVersion());
        return employeeForm;
    }

    public Employee convert(EmployeeForm employeeForm) {
        Employee employee = new Employee();
        // dates will be set on the way into the database
        // database id will be obtained from db
        employee.setLastName(employeeForm.getLastName());
        employee.setFirstName(employeeForm.getFirstName());
        employee.setEmployeeId(employeeForm.getEmployeeId());
        employee.setVersion(employeeForm.getVersion());
        return employee;
    }

}
