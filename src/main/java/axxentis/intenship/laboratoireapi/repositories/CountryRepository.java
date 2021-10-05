package axxentis.intenship.laboratoireapi.repositories;

import axxentis.intenship.laboratoireapi.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Boolean existsByLibelle(String libelle);

    Optional<Country> findByLibelle(String libelle);

    @Query("" +
            "SELECT CASE WHEN COUNT(p) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Country p " +
            "WHERE p.isoCode = ?1"
    )
    Boolean existsByIsoCode(String isoCode);

    Optional<Country> findByIsoCode(String isoCode);


}
