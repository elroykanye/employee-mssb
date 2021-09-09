package axxentis.intenship.laboratoireapi.services;

import axxentis.intenship.laboratoireapi.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

   List<Employee> getAllEmployees();

    Optional<Employee> findEmployeeById(Long id);

    Employee addEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);


}
