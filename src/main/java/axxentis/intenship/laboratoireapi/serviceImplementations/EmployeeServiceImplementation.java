package axxentis.intenship.laboratoireapi.serviceImplementations;

import axxentis.intenship.laboratoireapi.entities.Employee;
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
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Optional<Employee> employeeToUpdate = findEmployeeById(id);
        employeeToUpdate.get().setEmail(employee.getEmail());
        return employeeRepository.save(employeeToUpdate.get());
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

}
