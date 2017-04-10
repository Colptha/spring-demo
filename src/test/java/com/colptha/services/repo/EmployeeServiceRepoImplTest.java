package com.colptha.services.repo;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.services.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Map;

/**
 * Created by Colptha on 4/7/17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeServiceRepoImplTest {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
        System.out.println(employeeService.getClass());
    }

    @Test
    public void testInitializer() throws Exception {
        assert employeeService.toString() != null;
    }

    @Test
    public void testListAll() throws Exception {
        Map<String, EmployeeForm> testList = employeeService.listAll();
        assert testList.size() > 0;
    }

    @Test
    public void testFindOne() throws Exception {
        EmployeeForm employeeForm = employeeService.findOne("aa1");
        assert employeeForm.getClass() == EmployeeForm.class;
        assert employeeForm.getFirstName().equals("John");
        assert employeeForm.getLastName().equals("Gill");
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        String firstName = "first";
        String lastName = "last";
        String id = "zz7";
        String newFirstName = "Tom";

        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setFirstName(firstName);
        employeeForm.setLastName(lastName);
        employeeForm.setEmployeeId(id);
        EmployeeForm savedEmployee = employeeService.saveOrUpdate(employeeForm);

        Date createdOn = savedEmployee.getCreatedOn();
        Date updatedOn = savedEmployee.getUpdatedOn();

        assert savedEmployee.getLastName().equals(lastName);
        assert savedEmployee.getFirstName().equals(firstName);
        assert savedEmployee.getEmployeeId().equals(id);
        assert createdOn != null;
        assert savedEmployee.getUpdatedOn() != null;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        savedEmployee.setFirstName(newFirstName);

        EmployeeForm reSavedEmployee = employeeService.saveOrUpdate(savedEmployee);

        assert reSavedEmployee.getFirstName().equals(newFirstName);
        System.out.println("########");
        System.out.println("Old created on: " + createdOn.getTime());
        System.out.println("Old updated on: " + updatedOn.getTime());
        System.out.println("New created on: " + reSavedEmployee.getCreatedOn().getTime());
        System.out.println("New updated on: " + reSavedEmployee.getUpdatedOn().getTime());
        System.out.println("########");
        assert reSavedEmployee.getCreatedOn().getTime() == createdOn.getTime();
        assert reSavedEmployee.getUpdatedOn() != savedEmployee.getUpdatedOn();
    }

    @Test
    public void testDelete() throws Exception {
        Integer numberOfEmployees = employeeService.listAll().size();
        String id = employeeService.listAll().values().iterator().next().getEmployeeId();
        employeeService.delete(id);
        assert employeeService.listAll().size() == numberOfEmployees - 1;
    }
}
