package axxentis.intenship.laboratoireapi.repositories;


import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface VerificationRepository extends JpaRepository <Verification, Long> {
    List<Verification> findAllByEmployee(Employee employee);
    Optional<Verification> findByCode(String code);
}
