package axxentis.intenship.laboratoireapi.services;

import axxentis.intenship.laboratoireapi.entities.Department;
import axxentis.intenship.laboratoireapi.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    // Get all emplyees
     List<Department> getDepartments();

    // Get deparment by Id
     Optional<Department> getDepartment(Long id);

    //Create employee
     Department createDepartment(Department department);

    // Update Department
     Department updateDepartment(Long id);

    // Delete Department by Id
    void deleteDepartment(Long id);
}
