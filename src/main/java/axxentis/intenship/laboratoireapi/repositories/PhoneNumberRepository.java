package axxentis.intenship.laboratoireapi.repositories;

import axxentis.intenship.laboratoireapi.entities.Country;
import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author donatien
 * @created 27/07/2021 - 2:40 PM
 * @project utilisateur-service
 */

@Repository
public interface PhoneNumberRepository extends JpaRepository <PhoneNumber, Long> {
    Boolean existsPhoneNumberByNumero(String numero);
    Boolean existsPhoneNumberByNumeroAndCountry(String numero, Country country);
    Boolean existsPhoneNumberByEmployeeAndIsPrincipalTrue(Employee employee);
    Optional<PhoneNumber> findByEmployeeAndIsPrincipalTrue(Employee employee);
    Optional<PhoneNumber> findPhoneNumberByNumero(String numero);
    Optional<PhoneNumber> findByNumeroAndCountry(String numero, Country country);
    List<PhoneNumber> findAllByEmployee(Employee employee);



}
