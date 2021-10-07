package axxentis.intenship.laboratoireapi.repositories;



import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.VerifyEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VerifyEmployeeRepository extends JpaRepository<VerifyEmployee, Long> {
    List<VerifyEmployee> findAllByEmployee(Employee employee);
    Optional<VerifyEmployee> findByCode(String code);
}
