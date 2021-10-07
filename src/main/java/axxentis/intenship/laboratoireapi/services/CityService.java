package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.entities.Country;
import axxentis.intenship.laboratoireapi.entities.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    City addCity(City city);

    City updateCity(City city);

   List<City> findAllCity();

    List<City> SearchCity(String searchKey);

    Boolean existeCityByLibelle(String libelle);

    Optional<City> findCityById(Long id);

    Optional<City> findCityByLibelleAndCountry(String libelle, Country country);


    Optional<City> findCityByLibelle(String isoCode);

    void deleteCity(Long id);
}
