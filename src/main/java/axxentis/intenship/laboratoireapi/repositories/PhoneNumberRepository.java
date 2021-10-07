package axxentis.intenship.laboratoireapi.repositories;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.PhoneNumber;
import axxentis.intenship.laboratoireapi.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface PhoneNumberRepository extends JpaRepository <PhoneNumber, Long> {
    Boolean existsPhoneNumberByNumber(String number);
    Boolean existsPhoneNumberByNumberAndCountry(String number, Country country);
    Boolean existsPhoneNumberByEmployeeAndIsPrincipalTrue(Employee employee);
    Optional<PhoneNumber> findByEmployeeAndIsPrincipalTrue(Employee employee);
    Optional<PhoneNumber> findPhoneNumberByNumber(String number);
    Optional<PhoneNumber> findByNumberAndCountry(String number, Country country);
    List<PhoneNumber> findAllByEmployee(Employee employee);



}
