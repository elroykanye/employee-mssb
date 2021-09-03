package axxentis.intenship.laboratoireapi.serviceImplementations;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Image;
import axxentis.intenship.laboratoireapi.repositories.EmployeeRepository;
import axxentis.intenship.laboratoireapi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class EmployeeServiceImplementation implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;


    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Employee addEmployee(Employee employee) {
        Employee newEmployee = new Employee();
        newEmployee.setEmail(employee.getEmail());
        return employeeRepository.save(newEmployee);
    }

    @Override
    public Employee updateEmployee(Long employeeId, Employee employee) {
        Optional<Employee> employeeToUpdate = findEmployeeById(employeeId);
        employeeToUpdate.get().setEmail(employee.getEmail());
        return employeeRepository.save(employeeToUpdate.get());
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeRepository.deleteAll();
    }

}
