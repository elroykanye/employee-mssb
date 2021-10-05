package axxentis.intenship.laboratoireapi.servicesImpl;


import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.EmployeeProfil;
import axxentis.intenship.laboratoireapi.entities.Profil;
import axxentis.intenship.laboratoireapi.repositories.EmployeeProfilRepository;
import axxentis.intenship.laboratoireapi.services.EmployeeProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class EmployeeProfilServiceImpl implements EmployeeProfilService {
    @Autowired
    private final EmployeeProfilRepository employeeProfilRepository;

    public EmployeeProfilServiceImpl(EmployeeProfilRepository employeeProfilRepository) {
        this.employeeProfilRepository = employeeProfilRepository;
    }



    @Override
    public Boolean existsEmployeeProfilByEmployeeAndProfil(Employee employee, Profil profil) {
        return employeeProfilRepository.existsByEmployeeAndProfil(employee, profil);
    }

    @Override
    public Optional<EmployeeProfil> findEmployeeProfilById(Long id) {
        return employeeProfilRepository.findById(id);
    }

    @Override
    public List<EmployeeProfil> findEmployeeProfilByEmployee(Employee employee) {
        return employeeProfilRepository.findEmployeeProfilByEmployee(employee);
    }

    @Override
    public Optional<EmployeeProfil> findEmployeeProfilByEmployeeAndProfil(Employee employee, Profil profil) {
        return employeeProfilRepository.findEmployeeProfilByEmployeeAndProfil(employee, profil);
    }

    @Override
    public EmployeeProfil saveEmployeeProfil(EmployeeProfil employeeProfil) {
        return employeeProfilRepository.save(employeeProfil);
    }




    @Override
    public List<EmployeeProfil> getAllEmployeeProfil() {
        return employeeProfilRepository.findAll();
    }

    @Override
    public List<EmployeeProfil> getAllByEmployee(Employee employee) {
        return employeeProfilRepository.findAllByEmployee(employee);
    }

    @Override
    public void deleteById(Long id) {
        employeeProfilRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        employeeProfilRepository.deleteAll();
    }
}
