package com.colptha.services.security;

import com.colptha.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by Colptha on 4/22/17.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {
        Optional<UserDetails> userDetails = Optional.ofNullable(employeeService.findUserDetails(employeeId));
        if (!userDetails.isPresent()) {
            throw new UsernameNotFoundException("This ID does not exist");
        }
        return userDetails.get();
    }

}
