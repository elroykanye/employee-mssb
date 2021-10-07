package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.PhoneNumber;
import axxentis.intenship.laboratoireapi.entities.Country;

import java.util.List;
import java.util.Optional;

public interface PhoneNumberService {
    Optional<PhoneNumber> findNumberTelePhonePrincipaleByEmployee(Employee employee);
    Optional<PhoneNumber> findNumberTelePhoneByNumber(String number);
    List<PhoneNumber> getAllPhoneNumberByEmployee(Employee employee);
    Boolean existPhoneNumberByNumber(String number);
    Boolean existPhoneNumberByNumberAndCountry(String number, Country country);
    Boolean existPhoneNumberByEmployeeAndIsPrincipalTrue(Employee employee);
    PhoneNumber addPhoneNumber(PhoneNumber phoneNumber);
    PhoneNumber updatePhoneNumber(PhoneNumber phoneNumber);
    void deletePhoneNumber(Long id);
}
