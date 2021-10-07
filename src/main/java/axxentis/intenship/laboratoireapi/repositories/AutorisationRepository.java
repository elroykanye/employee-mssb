package axxentis.intenship.laboratoireapi.repositories;


import axxentis.intenship.laboratoireapi.entities.Autorisation;
import axxentis.intenship.laboratoireapi.entities.Privilege;
import axxentis.intenship.laboratoireapi.entities.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface AutorisationRepository extends JpaRepository<Autorisation, Long> {
    Boolean existsByProfilAndPrivilege(Profil profil, Privilege privilege);
    Optional<Autorisation> findAutorisationByProfilAndPrivilege(Profil profil, Privilege privilege);
    Optional<Autorisation> findAutorisationByProfil(Profil profil);
    List<Autorisation> findAllByProfilId(Long autorisationId);
}
