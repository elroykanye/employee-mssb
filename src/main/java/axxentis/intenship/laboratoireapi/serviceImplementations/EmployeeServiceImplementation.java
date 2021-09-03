package axxentis.intenship.laboratoireapi.serviceImplementations;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.repositories.EmployeeRepository;
import axxentis.intenship.laboratoireapi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class EmployeeServiceImplementation implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;


    @Override
    public Employee findEmployeeByEmail(String email) {
        Boolean existEmployee = employeeRepository.existsByEmail(email);
        if (existEmployee) {
            Optional<Employee> employee = employeeRepository.findByEmail(email);
            return employee.get();
        } else {
            return new Employee();
        }
    }

    @Override
    public List<Employee> findAllMales() {
        return employeeRepository.findMaleEmployees();
    }

    @Override
    public boolean existEmployeeEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return null;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public Employee deleteEmployee(Employee employee) {
        return null;
    }

}
