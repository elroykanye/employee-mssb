package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.entities.Country;
import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.PhoneNumber;

import java.util.List;
import java.util.Optional;



public interface PhoneNumberService {
    Optional<PhoneNumber> findNumeroTelePhonePrincipaleByEmployee(Employee employee);
    Optional<PhoneNumber> findNumeroTelePhoneByNumber(String numero);
    List<PhoneNumber> getAllPhoneNumberByEmployee(Employee employee);
    Boolean existPhoneNumberByNumber(String numero);
    Boolean existPhoneNumberByNumberAndCountry(String numero, Country country);
    Boolean existPhoneNumberByEmployeeAndIsPrincipalTrue(Employee employee);
    PhoneNumber addPhoneNumber(PhoneNumber numeroTelephone);
    PhoneNumber updatePhoneNumber(PhoneNumber numeroTelephone);
    void deletePhoneNumber(Long id);
}
