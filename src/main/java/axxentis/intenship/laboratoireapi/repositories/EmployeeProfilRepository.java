package axxentis.intenship.laboratoireapi.repositories;


import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.EmployeeProfil;
import axxentis.intenship.laboratoireapi.entities.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeProfilRepository extends JpaRepository <EmployeeProfil, Long> {

    Boolean existsByEmployeeAndProfil(Employee employee, Profil profil);
    Optional<EmployeeProfil> findEmployeeProfilByProfil(Profil profil);
    Optional<EmployeeProfil> findEmployeeProfilByEmployeeAndProfil(Employee employee, Profil profil);
    List<EmployeeProfil> findAllByEmployee(Employee employee);
    List<EmployeeProfil> findEmployeeProfilByEmployee(Employee employee);
}
