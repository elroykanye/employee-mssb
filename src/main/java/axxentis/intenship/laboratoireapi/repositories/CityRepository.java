package axxentis.intenship.laboratoireapi.repositories;


import axxentis.intenship.laboratoireapi.entities.Country;
import axxentis.intenship.laboratoireapi.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Boolean existsByLibelle(String libelle);

    Optional<City> findByLibelle(String libelle);
    Optional<City> findByLibelleAndCountry(String libelle, Country country);

    List<City> findCityByLibelleContains(String searchKey);
}
