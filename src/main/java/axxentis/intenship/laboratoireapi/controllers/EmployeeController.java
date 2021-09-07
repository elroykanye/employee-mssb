package axxentis.intenship.laboratoireapi.controllers;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.payload.responses.ApiResponse;
import axxentis.intenship.laboratoireapi.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping(value = "/all")
    public ResponseEntity<?>getAll(){
        List<Employee> employees = employeeService.getAllEmployees();
        if (!CollectionUtils.isEmpty(employees)){
            return ResponseEntity.ok(new ApiResponse(true, employees, "Employees loaded successfully" , HttpStatus.OK));
        }else {
            return ResponseEntity.ok(new ApiResponse(false, employees, "Failed to load employees, empty List", HttpStatus.NOT_FOUND));
        }
    }

    @GetMapping(value = "{/id}")
    public ResponseEntity<?>getEmployee(@PathVariable(value = "id") final Long id){
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        if (employee.isPresent()){
            return ResponseEntity.ok(new ApiResponse(true, employee, "Employee loaded successfully", HttpStatus.OK));
        }else {
            return ResponseEntity.ok(new ApiResponse(false, employee, "Unsuccessful attempt, employee not found", HttpStatus.NOT_FOUND));
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        Employee newEmployee = employeeService.addEmployee(employee);
        if (employee.getEmail().isBlank() || employee.getEmail().isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(false, employee, "Please enter the employee email!", HttpStatus.PARTIAL_CONTENT));
        } else {
            return ResponseEntity.ok(new ApiResponse(true, newEmployee, "Employee added successfully", HttpStatus.OK));
        }

    }


    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?>updateEmployee(@PathVariable(value = "id") final Long id, @RequestBody Employee employee){
        Employee newEmployee = employeeService.updateEmployee(id, employee);
        if (employeeService.findEmployeeById(id).isEmpty()){
            return ResponseEntity.ok(new ApiResponse(false, employee, "Employee id " + id + " not found", HttpStatus.NOT_FOUND));
        }else {
            return ResponseEntity.ok(new ApiResponse(true, newEmployee, "Employee updated Successfully", HttpStatus.OK));
        }

    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?>deleteEmployee(@PathVariable(value = "id") final Long id){
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new ApiResponse(true, "Employee deleted Successfully", HttpStatus.OK));
    }
}
