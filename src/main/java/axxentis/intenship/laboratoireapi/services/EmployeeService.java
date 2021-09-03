package axxentis.intenship.laboratoireapi.services;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Image;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public List<Employee> getAllEmployees();
    Optional<Employee> findEmployeeById(Long employeeId);

    public Employee addEmployee(Employee employee);

    public Employee updateEmployee(Long employeeId, Employee employee);

    void deleteEmployee(Employee employee);


}
