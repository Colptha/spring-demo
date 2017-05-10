package com.colptha.dom.validators;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.validators.interfaces.EmployeeFormValidator;
import com.colptha.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Colptha on 4/17/17.
 */
@Component
public class EmployeeFormValidatorImpl implements EmployeeFormValidator {
    private final Pattern namePattern = Pattern.compile("^[a-zA-Z]{2,}([ \\-][a-zA-Z]+[.]?)?$");
    private final Pattern addressLine1Pattern = Pattern.compile("^\\d+([ \\-][a-zA-Z]+[.]?){1,3}$");
    private final Pattern addressLine2Pattern = Pattern.compile("^([A-Z][a-zA-Z]+[.]?[ ])?[\\d]+?[a-zA-Z]?$");

    private final Pattern cityPattern = Pattern.compile("^([A-Z][a-zA-Z]+[.]?[ ])?[a-zA-Z]+([ \\-]?[a-zA-Z]+)*$");
    private final Pattern statePattern = Pattern.compile("^[A-Z]{2}$");
    private final Pattern zipcodePattern = Pattern.compile("^\\d{5}([-]\\d{4})?$");

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return EmployeeForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EmployeeForm employeeForm = (EmployeeForm) o;
        Matcher firstNameMatcher = namePattern.matcher(employeeForm.getFirstName());
        Matcher lastNameMatcher = namePattern.matcher(employeeForm.getLastName());
        Matcher addressLine1Matcher = addressLine1Pattern.matcher(employeeForm.getAddress().getLine1());
        Matcher addressLine2Matcher = addressLine2Pattern.matcher(employeeForm.getAddress().getLine2());
        Matcher cityMatcher = cityPattern.matcher(employeeForm.getAddress().getCity());
        Matcher stateMatcher = statePattern.matcher(employeeForm.getAddress().getState());
        Matcher zipcodeMatcher = zipcodePattern.matcher(employeeForm.getAddress().getZipcode());

        if (!firstNameMatcher.find()) {
            errors.rejectValue("firstName", "InvalidFirstName", "First Name is invalid");
        }
        if (!lastNameMatcher.find()) {
            errors.rejectValue("lastName", "InvalidLastName", "Last Name is invalid");
        }
        if (!addressLine1Matcher.find()) {
            errors.rejectValue("address.line1", "InvalidAddressLine1", "Line 1 of address is invalid");
        }
        if (employeeForm.getAddress().getLine2() != null &&
                !employeeForm.getAddress().getLine2().isEmpty() &&
                !addressLine2Matcher.find()) {
            errors.rejectValue("address.line2", "InvalidAddressLine2","Line 2 of address is invalid");
        }
        if (!cityMatcher.find()) {
            errors.rejectValue("address.city", "InvalidCity", "City is invalid");
        }
        if (!stateMatcher.find()) {
            errors.rejectValue("address.state", "InvalidState", "Enter state abbreviation, cf 'IL'");
        }
        if (!zipcodeMatcher.find()) {
            errors.rejectValue("address.zipcode", "InvalidZipcode", "Zipcode is invalid");
        }
        if (employeeForm.getEmployeeId() != null) {
            employeeForm.setEmployeeId(employeeForm.getEmployeeId().toUpperCase());
        }

        if (employeeService.isIdInUse(employeeForm) && employeeForm.isNewEmployee()) {
            errors.rejectValue("employeeId", "EmployeeIdInUse", "The employee ID must be unique");
        }


        if (employeeForm.isNewEmployee() && !employeeForm.getPassword().equals(employeeForm.getConfirmPassword())) {
            errors.rejectValue("password", "PasswordsDoNotMatch", "Passwords must match");
        }

        if (employeeForm.isNewEmployee() &&
                (employeeForm.getPassword().length() < 8 || employeeForm.getPassword().length() > 25)) {
            errors.rejectValue("password","PasswordIncorrectLength", "Password must be at least 8 and no more than 25 characters");
        }

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
