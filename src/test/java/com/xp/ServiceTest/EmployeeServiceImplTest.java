package com.xp.ServiceTest;

import com.xp.Model.Employee;
import com.xp.Repository.EmployeeRepository;
import com.xp.Service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repo;

    @InjectMocks
    private EmployeeServiceImpl service;

    private Employee emp;

    @BeforeEach
    void setUp() {
        emp = new Employee(
                "mabr0011",
                "Markus",
                "password",
                "admin"
        );
    }

    @Test
    void getEmployeeByUsername_ReturnsEmployee() {
        when(repo.findEmployeeByEmployeeUsername("mabr0011")).thenReturn(emp);

        Employee result = service.findEmployeeByUsername("mabr0011");

        assertEquals(emp, result);
        verify(repo).findEmployeeByEmployeeUsername("mabr0011");
    }

    @Test
    void getEmployeeByUsername_ReturnsNullIfNotFound() {
        when(repo.findEmployeeByEmployeeUsername("unknown")).thenReturn(null);

        assertNull(service.findEmployeeByUsername("unknown"));
        verify(repo).findEmployeeByEmployeeUsername("unknown");
    }

    @Test
    void getAllEmployees_ReturnsList() {
        List<Employee> list = List.of(emp);
        when(service.findAll()).thenReturn(list);

        List<Employee> result = service.findAll();

        assertEquals(list, result);
        verify(service).findAll();
    }

    @Test
    void login_ReturnsTrue() {
        when(service.login("mabr0011", "password")).thenReturn(true);

        boolean result = service.login("mabr0011", "password");

        assertTrue(result);
        verify(service).login("mabr0011", "password");
    }

    @Test
    void login_ReturnsFalse() {
        when(service.login("mabr0011", "wrong")).thenReturn(false);

        boolean result = service.login("mabr0011", "wrong");

        assertFalse(result);
        verify(service).login("mabr0011", "wrong");
    }
}






