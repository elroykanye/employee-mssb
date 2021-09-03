package axxentis.intenship.laboratoireapi.repositories;

import axxentis.intenship.laboratoireapi.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
