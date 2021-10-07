package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.entities.Autorisation;
import axxentis.intenship.laboratoireapi.entities.Privilege;
import axxentis.intenship.laboratoireapi.entities.Profil;

import java.util.List;
import java.util.Optional;

public interface AutorisationService {
    Boolean existsByProfilAndPrivilege(Profil profil, Privilege privilege);
    Optional<Autorisation> findAutorisationByProfilAndPrivilege(Profil profil, Privilege privilege);
    Optional<Autorisation> findAutorisationById(Long id);
    Optional<Autorisation> findAutorisationByProfil(Profil profil);
    public Autorisation saveAutorisation(Autorisation autorisation);
    List<Autorisation> findAllByProfilId(Long autorisationId);
    public void deleteById(Long autorisationId);
    public void deleteAll();
}
