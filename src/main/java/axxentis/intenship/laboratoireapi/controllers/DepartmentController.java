package axxentis.intenship.laboratoireapi.controllers;

import axxentis.intenship.laboratoireapi.entities.Department;
import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.payload.responses.ApiResponse;
import axxentis.intenship.laboratoireapi.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/department")
public class DepartmentController  {

    @Autowired
    DepartmentService departmentService;

    // Get all Departments
    @GetMapping("/all")
    public ResponseEntity<?>getAllDepartments(){
        List<Department> departments = departmentService.getDepartments();
        if (!CollectionUtils.isEmpty(departments)){
            return ResponseEntity.ok(new ApiResponse(true, departments, "get all successfuly!", HttpStatus.OK));
        }else {
            return ResponseEntity.ok(new ApiResponse(false, "The list is empty! ", HttpStatus.NOT_FOUND ));
        }

    }

    // Get Department by id
    @GetMapping("/{id}")
    public ResponseEntity<?>getDepartment(@PathVariable("id") final Long id){
        Optional<Department> department = departmentService.getDepartment(id);
        if (department.isPresent()){
            return ResponseEntity.ok(new ApiResponse(true, department, "Get succesfuklly", HttpStatus.OK));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Department nout found", HttpStatus.NOT_FOUND));
        }
    }

    // Create department
    @PostMapping("/add")
    public ResponseEntity<?>createDepartment(@RequestBody Department department){
        Department saveDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.ok(new ApiResponse(true, saveDepartment, "Save succesfully", HttpStatus.OK));
    }

    // Update departement
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable("id") final Long id, @RequestBody Department department){
        Optional<Department> d =departmentService.getDepartment(id);
        if (d.isPresent()){
            Department currentDepartment = d.get();

            String name = department.getName();
            System.out.println("Nom de departement : "+name);

            if (!name.isEmpty() || !name.isBlank()){
                currentDepartment.setName(name);
            }

            String description  = department.getDescription();
            if (!description.isEmpty() || !description.isBlank()){
                currentDepartment.setDescription(description);
            }

            return ResponseEntity.ok(new ApiResponse(true, departmentService.saveDepartment(currentDepartment), "Department updated succesfully", HttpStatus.OK));

        }else {
            return ResponseEntity.ok(new ApiResponse(false, "Department not found", HttpStatus.NO_CONTENT));
        }

    }

    // Delete department
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") final Long id){
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok(new ApiResponse(true, "Department deleted succesfully", HttpStatus.OK));
    }

}
