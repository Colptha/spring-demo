package com.colptha.services.repo;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.command.PasswordForm;
import com.colptha.dom.converters.EmployeeConverter;
import com.colptha.dom.converters.UserDetailsConverter;
import com.colptha.dom.entities.Employee;
import com.colptha.services.EmployeeService;
import com.colptha.services.repo.interfaces.EmployeeRepository;
import com.colptha.services.security.EncryptionService;
import com.colptha.services.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by Colptha on 4/4/17.
 */
@Service
@Profile({"repo", "default"})
public class EmployeeRepoServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeConverter employeeConverter;
    private EncryptionService encryptionService;
    private UserDetailsConverter userDetailsConverter;

    @Autowired
    public void setUserDetailsConverter(UserDetailsConverter userDetailsConverter) {
        this.userDetailsConverter = userDetailsConverter;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Autowired
    public void setEmployeeConverter(EmployeeConverter employeeConverter) {
        this.employeeConverter = employeeConverter;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Map<String, EmployeeForm> listAll() {
        Map<String, EmployeeForm> employeeFormMap = new HashMap<>();

        employeeRepository.findAll().forEach(
                employee -> employeeFormMap.put(employee.getEmployeeId(), employeeConverter.convert(employee)));

        return employeeFormMap;
    }

    @Override
    public EmployeeForm findOne(String query) throws NoSuchElementException {
        return employeeConverter.convert(employeeRepository.findByEmployeeId(query));
    }

    public UserDetails findUserDetails(String query) throws NoSuchElementException {
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByEmployeeId(query));
        return employee.map(e -> userDetailsConverter.convert(e)).orElse(null);
    }

    @Override
    public boolean isIdInUse(EmployeeForm employeeForm) {

        Optional<Employee> employee =
                Optional.ofNullable(employeeRepository.findByEmployeeId(employeeForm.getEmployeeId()));

        return employee.isPresent() && employee.get().getEmployeeId().equals(employeeForm.getEmployeeId());
    }

    @Override
    public EmployeeForm saveOrUpdate(EmployeeForm employeeForm) {
        Employee currentEmployee = employeeConverter.convert(employeeForm);

        if (employeeForm.isNewEmployee()) {
            String encryptedPassword = encryptionService.encryptPassword(currentEmployee.getPassword());
            currentEmployee.setEncryptedPassword(encryptedPassword);
        } else {
            Employee existingEmployee = employeeRepository.findByEmployeeId(currentEmployee.getEmployeeId());

            currentEmployee.setCreatedOn(existingEmployee.getCreatedOn());
            currentEmployee.setDatabaseId(existingEmployee.getDatabaseId());
            currentEmployee.setEncryptedPassword(existingEmployee.getEncryptedPassword());
            if (employeeForm.getRole() == null) {
                currentEmployee.setRole(existingEmployee.getRole().name());
            } else {
                currentEmployee.setRole(employeeForm.getRole());
            }

        }

        return employeeConverter.convert(employeeRepository.save(currentEmployee));
    }

    @Override
    public void changePassword(PasswordForm passwordForm, UserDetails userDetails) {
        Employee employee = employeeRepository.findByEmployeeId(userDetails.getUsername());

        String encryptedPassword = encryptionService.encryptPassword(passwordForm.getNewPassword());
        employee.setEncryptedPassword(encryptedPassword);
        // can downcast and change userdetails password
        // could display message that changes won't take effect until log back in
        UserDetailsImpl userDetails1 = (UserDetailsImpl) userDetails;
        userDetails1.setPassword(encryptedPassword);


        employeeRepository.save(employee);
    }

    @Override
    public void resetPassword(String id) {
        Employee employee = employeeRepository.findByEmployeeId(id);

        String encryptedPassword = encryptionService.encryptPassword(employee.getFirstName() + employee.getLastName());
        employee.setEncryptedPassword(encryptedPassword);

        employeeRepository.save(employee);
    }

    @Override
    public void delete(String query) {
        employeeRepository.delete(employeeRepository.findByEmployeeId(query));
    }
}
