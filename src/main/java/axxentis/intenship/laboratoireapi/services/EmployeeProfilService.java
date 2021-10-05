package axxentis.intenship.laboratoireapi.services;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.EmployeeProfil;
import axxentis.intenship.laboratoireapi.entities.Profil;

import java.util.List;
import java.util.Optional;


public interface EmployeeProfilService {
   Boolean existsEmployeeProfilByEmployeeAndProfil(Employee employee, Profil profil);
    Optional<EmployeeProfil> findEmployeeProfilById(Long id);
    List<EmployeeProfil> findEmployeeProfilByEmployee(Employee employee);

     Optional<EmployeeProfil> findEmployeeProfilByEmployeeAndProfil(Employee employee, Profil profil);
    public EmployeeProfil saveEmployeeProfil(EmployeeProfil employeeProfil);
    List<EmployeeProfil> getAllByEmployee(Employee employee);
    List<EmployeeProfil> getAllEmployeeProfil();
    public void deleteById(Long autorisationId);
    public void deleteAll();
}
