package com.colptha.dom.command;

/**
 * Created by Colptha on 4/1/17.
 */
public class EmployeeForm extends AbstractCommandObject {

    private String firstName;
    private String lastName;
    private String employeeId;

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
}
