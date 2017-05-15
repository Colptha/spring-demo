package com.colptha.dom.command;

import com.colptha.dom.entities.Address;

/**
 * Created by Colptha on 4/1/17.
 */
public class EmployeeForm extends AbstractCommandObject {
    
    private String firstName;
    private String lastName;
    private String employeeId;
    private Address address;
    private PasswordForm passwordForm = new PasswordForm();
    private String role;
    private boolean newEmployee = true;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isNewEmployee() {
        return newEmployee;
    }

    public void setNewEmployee(boolean newEmployee) {
        this.newEmployee = newEmployee;
    }

    public String getPassword() {
        return passwordForm.getNewPassword();
    }

    public void setPassword(String password) {
        passwordForm.setNewPassword(password);
    }

    public String getConfirmPassword() {
        return passwordForm.getConfirmPassword();
    }

    public void setConfirmPassword(String confirmPassword) {
        passwordForm.setConfirmPassword(confirmPassword);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static EmployeeForm createEmployeeForm() {
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setRole("USER");
        return employeeForm;
    }
}
