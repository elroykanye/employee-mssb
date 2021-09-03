package axxentis.intenship.laboratoireapi.services;

import axxentis.intenship.laboratoireapi.entities.Employee;

import java.util.List;

public interface EmployeeService {

    public Employee findEmployeeByEmail(String email);
    public List<Employee> findAllMales();

    public boolean existEmployeeEmail(String email);

    public Employee addEmployee(Employee employee);

    public Employee updateEmployee(Employee employee);

    public Employee deleteEmployee(Employee employee);


}
