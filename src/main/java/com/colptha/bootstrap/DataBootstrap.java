package com.colptha.bootstrap;

import com.colptha.dom.entities.Employee;
import com.colptha.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by Colptha on 3/31/17.
 */
@Component
public class DataBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadEmployees();
    }

    private void loadEmployees() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        Employee employee3 = new Employee();
        Employee employee4 = new Employee();
        Employee employee5 = new Employee();
        employee1.setEmployeeCode("aa1");
        employee1.setFirstName("John");
        employee1.setLastName("Gill");
        employee2.setEmployeeCode("aa2");
        employee2.setFirstName("Sandy");
        employee2.setLastName("Wilson");
        employee3.setEmployeeCode("aa3");
        employee3.setFirstName("Martin");
        employee3.setLastName("Bucer");
        employee4.setEmployeeCode("aa4");
        employee4.setFirstName("Bill");
        employee4.setLastName("Thompson");
        employee5.setEmployeeCode("aa5");
        employee5.setFirstName("Jason");
        employee5.setLastName("Ruby");

        employeeService.saveOrUpdate(employee1);
        employeeService.saveOrUpdate(employee2);
        employeeService.saveOrUpdate(employee3);
        employeeService.saveOrUpdate(employee4);
        employeeService.saveOrUpdate(employee5);
    }
}
