package axxentis.intenship.laboratoireapi.servicesImpl;


import axxentis.intenship.laboratoireapi.entities.Department;
import axxentis.intenship.laboratoireapi.repositories.DepartmentRepository;
import axxentis.intenship.laboratoireapi.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private final DepartmentRepository departmentRepository;
    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        super();
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> findAllDepartment() {
        return departmentRepository.findAll();
    }



    @Override
    public Boolean existeDepartmentByLibelle(String libelle) {
        return departmentRepository.existsByLibelle(libelle);
    }

    @Override
    public Optional<Department> findDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }



    @Override
    public Optional<Department> findDepartmentByLibelle(String name) {
        return departmentRepository.findByLibelle(name);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteDepartment(Long idDepartment) {
        departmentRepository.deleteById(idDepartment);
    }
}
