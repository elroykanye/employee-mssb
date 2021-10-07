package axxentis.intenship.laboratoireapi.repositories;


import axxentis.intenship.laboratoireapi.entities.Employee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    Optional<Employee> findByUsername(String username);
    Optional<Employee> findEmployeeByUsername(String username);
    Optional<Employee> findEmployeeByIdAndOnLineIsTrue(Long id);
    Optional<Employee> findEmployeeByEmail(String email);
    @Query("SELECT cts FROM Employee cts INNER JOIN" +
            " PhoneNumber phone ON cts = phone.employee WHERE" +
            " phone.number= ?1 AND" +
            " phone.isPrincipal = true ")
    Optional<Employee> findEmployeeByPhoneNumber(String number);

    List<Employee> findByLastNameContainingAndFirstNameContaining(String lastName, String firstName);
    List<Employee> findAll(Specification<Employee> employeeName);
}
