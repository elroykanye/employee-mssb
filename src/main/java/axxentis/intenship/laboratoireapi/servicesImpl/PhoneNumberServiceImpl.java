package axxentis.intenship.laboratoireapi.servicesImpl;


import axxentis.intenship.laboratoireapi.entities.Country;
import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.PhoneNumber;
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
    private final PhoneNumberRepository numeroTelephoneRepository;

    public PhoneNumberServiceImpl(PhoneNumberRepository numeroTelephoneRepository) {
        super();
        this.numeroTelephoneRepository = numeroTelephoneRepository;
    }

    @Override
    public Optional<PhoneNumber> findNumeroTelePhonePrincipaleByEmployee(Employee employee) {
        return numeroTelephoneRepository.findByEmployeeAndIsPrincipalTrue(employee);
    }

    @Override
    public Optional<PhoneNumber> findNumeroTelePhoneByNumber(String number) {
        return numeroTelephoneRepository.findPhoneNumberByNumber(number);
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumberByEmployee(Employee employee) {
        return numeroTelephoneRepository.findAllByEmployee(employee);
    }

    @Override
    public Boolean existPhoneNumberByNumber(String numero) {
        return numeroTelephoneRepository.existsPhoneNumberByNumber(numero);
    }

    @Override
    public Boolean existPhoneNumberByNumberAndCountry(String numero, Country country) {
        return numeroTelephoneRepository.existsPhoneNumberByNumberAndCountry(numero, country);
    }

    @Override
    public Boolean existPhoneNumberByEmployeeAndIsPrincipalTrue(Employee employee) {
        return numeroTelephoneRepository.existsPhoneNumberByEmployeeAndIsPrincipalTrue(employee);
    }

    @Override
    public PhoneNumber addPhoneNumber(PhoneNumber numeroTelephone) {
       return numeroTelephoneRepository.save(numeroTelephone);
    }

    @Override
    public PhoneNumber updatePhoneNumber(PhoneNumber numeroTelephone) {
        return numeroTelephoneRepository.save(numeroTelephone);
    }

    @Override
    public void deletePhoneNumber(Long id) {
        numeroTelephoneRepository.deleteById(id);
    }
}
