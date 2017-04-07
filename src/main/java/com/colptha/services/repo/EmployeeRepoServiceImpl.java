package com.colptha.services.repo;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.converters.EmployeeConverter;
import com.colptha.services.EmployeeService;
import com.colptha.services.repo.interfaces.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Colptha on 4/4/17.
 */
@Service
@Profile({"repo", "default"})
public class EmployeeRepoServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeConverter employeeConverter;

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

    @Override
    public EmployeeForm saveOrUpdate(EmployeeForm employeeForm) {
        return employeeConverter.convert(employeeRepository.save(employeeConverter.convert(employeeForm)));
    }

    @Override
    public void delete(String query) {
        employeeRepository.delete(employeeRepository.findByEmployeeId(query));
    }
}
