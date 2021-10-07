package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.entities.Profil;

import java.util.List;
import java.util.Optional;

public interface ProfilService {
    List<Profil> findAllProfil();
    List<Profil> SearchProfil(String searchKey);
    Boolean existeProfilByLibelle(String libelle);

    Profil addProfil(Profil profil);
    Profil updateProfil(Profil profil);

    Optional<Profil> findProfilById(Long id);
    Optional<Profil> findProfilByLibelle(String libelle);
    void deleteProfil(Long id);
}
