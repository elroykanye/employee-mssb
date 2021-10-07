package axxentis.intenship.laboratoireapi.repositories;


import axxentis.intenship.laboratoireapi.entities.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author donatien
 * @created 27/07/2021 - 2:44 PM
 * @project utilisateur-service
 */

@Repository
public interface ProfilRepository extends JpaRepository <Profil, Long> {
    Boolean existsByLibelle(String libelle);
    Optional<Profil> findByLibelle(String libelle);
    List<Profil> findProfilByLibelleContains(String searchKey);
}
