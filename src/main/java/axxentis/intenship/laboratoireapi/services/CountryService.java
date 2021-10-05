package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.entities.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    public Country addCountry(Country country);

    public Country updateCountry(Country country);

    public List<Country> findAllCountry();

    Optional<Country> findCountryById(Long id);

    Optional<Country> findCountryByIsoCode(String codeIso);

    Optional<Country> findCountryByLibelle(String libelle);

    Boolean existCountryByIsoCode(String codeIso);
    Boolean existCountryByLibelle(String libelle);

    void deleteCountry(Long id);
}

