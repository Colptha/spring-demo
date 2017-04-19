package com.colptha.dom.validators;

import com.colptha.dom.command.EmployeeForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Colptha on 4/17/17.
 */
@Component
public class EmployeeFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return EmployeeForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EmployeeForm employeeForm = (EmployeeForm) o;

        if (employeeForm.getFirstName() == null || employeeForm.getFirstName().isEmpty()) {
            errors.rejectValue("firstName", "FirstNameEmpty", "First Name cannot be empty");
        }

        if (employeeForm.getLastName() == null || employeeForm.getLastName().isEmpty()) {
            errors.rejectValue("lastName", "LastNameEmpty", "Last Name cannot be empty");
        }

        if (employeeForm.getAddress().getLine1() == null || employeeForm.getAddress().getLine1().isEmpty()) {
            errors.rejectValue("address.line1", "AddressLine1Empty", "Address Line 1 cannot be empty");
        }

        if (employeeForm.getAddress().getCity() == null || employeeForm.getAddress().getCity().isEmpty()) {
            errors.rejectValue("address.city", "CityEmpty", "City cannot be empty");
        }

        if (employeeForm.getAddress().getState() == null || employeeForm.getAddress().getState().isEmpty()) {
            errors.rejectValue("address.state", "StateEmpty", "State cannot be empty");
        }

        if (employeeForm.getAddress().getZipcode() == null || employeeForm.getAddress().getZipcode().isEmpty()) {
            errors.rejectValue("address.zipcode", "ZipcodeEmpty", "Zipcode cannot be empty");
        }
    }
}