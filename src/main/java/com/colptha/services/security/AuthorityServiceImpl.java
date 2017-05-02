package com.colptha.services.security;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by Colptha on 5/1/17.
 */
@Component
public class AuthorityServiceImpl implements AuthorityService {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public boolean hasAuthorityToEditEmployee(EmployeeForm employeeForm, UserDetails userDetails) {
        EmployeeForm oldEmployeeInfo = new EmployeeForm();
        if (!employeeForm.isNewEmployee()) {
            oldEmployeeInfo = employeeService.findOne(employeeForm.getEmployeeId());

            if (employeeForm.getRole() == null) {
                employeeForm.setRole(oldEmployeeInfo.getRole());
            }
        }
        if (employeeForm.isNewEmployee()) {
            return true;
        }

        String employeeFormRole = employeeForm.getRole();
        String userDetailsRole = userDetails.getAuthorities().toArray()[0].toString().replace("ROLE_", "");

        if (userDetailsRole.equals("USER")) {
            return false;
        }

        if (userDetailsRole.equals("ADMIN")) {

            // admin cannot edit other admins but can edit their own information and everyone else
            // admin can promote another user to admin (comparison must be made to old data)
            return !employeeFormRole.equals("ADMIN") ||
                    userDetails.getUsername().equals(employeeForm.getEmployeeId()) ||
                    (!employeeForm.isNewEmployee() && !oldEmployeeInfo.getRole().equals("ADMIN"));
        }

        if (userDetailsRole.equals("MANAGER")) {
            if (employeeFormRole.equals("MANAGER")) {
                return userDetails.getUsername().equals(employeeForm.getEmployeeId());
            }
            if (employeeFormRole.equals("USER")) {
                return true;
            }
        }


        return false;
    }
}
