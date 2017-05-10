package com.colptha.controllers;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.validators.EmployeeFormValidatorImpl;
import com.colptha.services.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Created by Colptha on 5/9/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        employeeController.setEmployeeFormValidator(new EmployeeFormValidatorImpl());

        mockMvc = MockMvcBuilders
                .standaloneSetup(employeeController)
                .build();
    }

    @Test
    public void rootRedirectTest() throws Exception {
        mockMvc.perform(get("/employee/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employee/all"));
    }

    @Test
    public void rootTest() throws Exception {
        Map<String, EmployeeForm> employeeFormMap = new TreeMap<>();

        EmployeeForm employeeForm = new EmployeeForm();
        String employeeId = "A001";

        employeeFormMap.put(employeeId, employeeForm);

        when(employeeService.listAll()).thenReturn(employeeFormMap);

        mockMvc.perform(get("/employee/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/all"))
                .andExpect(model().attribute("employees", hasEntry(employeeId, employeeForm)));
    }

    @Test
    public void newEmployeeTest() throws Exception {
        mockMvc.perform(get("/employee/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/form"))
                .andExpect(model().attribute("employeeForm", instanceOf(EmployeeForm.class)));
    }

    @Test
    public void showOneTest() throws Exception {
        EmployeeForm employeeForm = new EmployeeForm();
        String employeeId = "A001";
        employeeForm.setEmployeeId(employeeId);

        when(employeeService.findOne(employeeId)).thenReturn(employeeForm);

        mockMvc.perform(get("/employee/show/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/show"))
                .andExpect(model().attribute("employee", hasProperty("employeeId", is(employeeId))));
    }

}