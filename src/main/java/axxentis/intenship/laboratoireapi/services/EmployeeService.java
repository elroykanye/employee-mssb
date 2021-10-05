package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.entities.Employee;

import java.util.List;
import java.util.Optional;


public interface EmployeeService {
    public void addEmployee(Employee employee);

    public Boolean existsEmployeeByUsername(String username);
    public Boolean existsEmployeeByEmail(String email);

    void updateEmployee(Employee employee);

    public List<Employee> findAllEmployee();

    public List<Employee> SearchEmployee(String searchKey);


    Optional<Employee> findEmployeeById(Long id);
    Optional<Employee> findEmployeeByIdAndIsOnLineIsTrue(Long id);
    Optional<Employee> findEmployeeByUsername(String username);
    Optional<Employee> findEmployeeByEmail(String email);
    Optional<Employee> findEmployeeByPhoneNumber(String phoneNumber);

    void deleteEmployee(Long id);

    List<Employee> findByCriteria(String employeeName);
}
