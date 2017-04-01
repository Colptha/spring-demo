package com.colptha.dom.entities;


import javax.persistence.Entity;

/**
 * Created by Colptha on 3/31/17.
 */
@Entity
public class Employee extends AbstractEntityObject {


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
