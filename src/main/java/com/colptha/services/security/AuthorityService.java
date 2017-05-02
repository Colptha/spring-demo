package com.colptha.services.security;

import com.colptha.dom.command.EmployeeForm;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Colptha on 5/1/17.
 */
public interface AuthorityService {
    boolean hasAuthorityToEditEmployee(EmployeeForm employeeForm, UserDetails userDetails);
}
