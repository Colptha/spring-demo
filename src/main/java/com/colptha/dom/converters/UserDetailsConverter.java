package com.colptha.dom.converters;

import com.colptha.dom.entities.Employee;
import com.colptha.services.security.UserDetailsImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by Colptha on 4/26/17.
 */
@Component
public class UserDetailsConverter implements Converter<Employee, UserDetails> {
    @Override
    public UserDetails convert(Employee employee) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(employee.getEmployeeId());
        userDetails.setPassword(employee.getEncryptedPassword());
        userDetails.getAuthorities().add(new SimpleGrantedAuthority("ROLE_" + employee.getRole().toString()));
        return userDetails;
    }
}
