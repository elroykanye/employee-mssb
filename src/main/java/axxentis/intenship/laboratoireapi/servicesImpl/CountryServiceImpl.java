package axxentis.intenship.laboratoireapi.servicesImpl;


import axxentis.intenship.laboratoireapi.entities.Country;
import axxentis.intenship.laboratoireapi.repositories.CountryRepository;
import axxentis.intenship.laboratoireapi.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Service
public class CountryServiceImpl implements CountryService {
    
    private CountryRepository countryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        super();
        this.countryRepository = countryRepository;
    }


    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country updateCountry(Country country) {
        return  countryRepository.save(country);
    }

    @Override
    public List<Country> findAllCountry() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> findCountryById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Optional<Country> findCountryByIsoCode(String isoCode) {
        return countryRepository.findByIsoCode(isoCode);
    }

    @Override
    public Boolean existCountryByIsoCode(String isoCode) {
        return countryRepository.existsByIsoCode(isoCode);
    }

    @Override
    public Boolean existCountryByLibelle(String libelle) {
        return countryRepository.existsByLibelle(libelle);
    }

    @Override
    public Optional<Country> findCountryByLibelle(String libelle) {
        return countryRepository.findByLibelle(libelle);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteCountry(Long idCountry) {
        countryRepository.deleteById(idCountry);
    }
}

