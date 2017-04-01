package com.colptha.dom.entities;


import javax.persistence.Entity;

/**
 * Created by Colptha on 3/31/17.
 */
@Entity
public class Employee extends AbstractEntityObject {


    private String firstName;
    private String lastName;
    private String employeeCode;

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

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
}
