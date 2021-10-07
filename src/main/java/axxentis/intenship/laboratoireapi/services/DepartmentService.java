package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.entities.Department;
import axxentis.intenship.laboratoireapi.entities.Country;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Department addDepartment(Department department);

    Department updateDepartment(Department department);

   List<Department> findAllDepartment();


    Boolean existeDepartmentByLibelle(String libelle);

    Optional<Department> findDepartmentById(Long id);


    Optional<Department> findDepartmentByLibelle(String isoCode);

    void deleteDepartment(Long id);
}
