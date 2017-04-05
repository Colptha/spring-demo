package com.colptha.services.repo.interfaces;

import com.colptha.dom.entities.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Colptha on 4/4/17.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    Employee findByEmployeeId(String employeeId);
}
