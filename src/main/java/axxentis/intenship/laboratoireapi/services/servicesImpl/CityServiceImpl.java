package axxentis.intenship.laboratoireapi.services.servicesImpl;


import axxentis.intenship.laboratoireapi.entities.Country;
import axxentis.intenship.laboratoireapi.entities.City;
import axxentis.intenship.laboratoireapi.repositories.CityRepository;
import axxentis.intenship.laboratoireapi.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private final CityRepository cityRepository;
    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        super();
        this.cityRepository = cityRepository;
    }

    @Override
    public City addCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City updateCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public List<City> findAllCity() {
        return cityRepository.findAll();
    }

    @Override
    public List<City> SearchCity(String searchKey) {
        return cityRepository.findCityByLibelleContains(searchKey);
    }

    @Override
    public Boolean existeCityByLibelle(String libelle) {
        return cityRepository.existsByLibelle(libelle);
    }

    @Override
    public Optional<City> findCityById(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public Optional<City> findCityByLibelleAndCountry(String libelle, Country country) {
        return cityRepository.findByLibelleAndCountry(libelle, country);
    }

    @Override
    public Optional<City> findCityByLibelle(String name) {
        return cityRepository.findByLibelle(name);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteCity(Long idCity) {
        cityRepository.deleteById(idCity);
    }
}
