package axxentis.intenship.laboratoireapi.servicesImpl;


import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.PhoneNumber;
import axxentis.intenship.laboratoireapi.entities.Country;
import axxentis.intenship.laboratoireapi.repositories.PhoneNumberRepository;
import axxentis.intenship.laboratoireapi.services.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {
    @Autowired
    private final PhoneNumberRepository phoneNumberRepository;

    public PhoneNumberServiceImpl(PhoneNumberRepository phoneNumberRepository) {
        super();
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @Override
    public Optional<PhoneNumber> findNumberTelePhonePrincipaleByEmployee(Employee employee) {
        return phoneNumberRepository.findByEmployeeAndIsPrincipalTrue(employee);
    }

    @Override
    public Optional<PhoneNumber> findNumberTelePhoneByNumber(String number) {
        return phoneNumberRepository.findPhoneNumberByNumber(number);
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumberByEmployee(Employee employee) {
        return phoneNumberRepository.findAllByEmployee(employee);
    }

    @Override
    public Boolean existPhoneNumberByNumber(String number) {
        return phoneNumberRepository.existsPhoneNumberByNumber(number);
    }

    @Override
    public Boolean existPhoneNumberByNumberAndCountry(String number, Country country) {
        return phoneNumberRepository.existsPhoneNumberByNumberAndCountry(number, country);
    }

    @Override
    public Boolean existPhoneNumberByEmployeeAndIsPrincipalTrue(Employee employee) {
        return phoneNumberRepository.existsPhoneNumberByEmployeeAndIsPrincipalTrue(employee);
    }

    @Override
    public PhoneNumber addPhoneNumber(PhoneNumber phoneNumber) {
       return phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public PhoneNumber updatePhoneNumber(PhoneNumber phoneNumber) {
        return phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public void deletePhoneNumber(Long id) {
        phoneNumberRepository.deleteById(id);
    }
}
