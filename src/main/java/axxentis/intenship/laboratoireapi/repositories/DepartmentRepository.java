package axxentis.intenship.laboratoireapi.repositories;

import axxentis.intenship.laboratoireapi.entities.City;
import axxentis.intenship.laboratoireapi.entities.Country;
import axxentis.intenship.laboratoireapi.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Boolean existsByLibelle(String libelle);

    Optional<Department> findByLibelle(String libelle);
}