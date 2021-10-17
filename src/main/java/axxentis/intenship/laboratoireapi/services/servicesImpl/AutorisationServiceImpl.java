package axxentis.intenship.laboratoireapi.services.servicesImpl;


import axxentis.intenship.laboratoireapi.entities.Autorisation;
import axxentis.intenship.laboratoireapi.entities.Privilege;
import axxentis.intenship.laboratoireapi.entities.Profil;
import axxentis.intenship.laboratoireapi.repositories.AutorisationRepository;
import axxentis.intenship.laboratoireapi.services.AutorisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;



@Transactional
@Service
public class AutorisationServiceImpl implements AutorisationService {

    @Autowired
    private final AutorisationRepository autorisationRepository;

    public AutorisationServiceImpl(AutorisationRepository autorisationRepository) {
        this.autorisationRepository = autorisationRepository;
    }

    @Override
    public Boolean existsByProfil(Profil profil) {
        return autorisationRepository.existsByProfil(profil);
    }

    @Override
    public Boolean existsByProfilAndPrivilege(Profil profil, Privilege privilege) {
        return autorisationRepository.existsByProfilAndPrivilege(profil, privilege);
    }

    @Override
    public Optional<Autorisation> findAutorisationByProfilAndPrivilege(Profil profil, Privilege privilege) {
        return autorisationRepository.findAutorisationByProfilAndPrivilege(profil,privilege);
    }

    @Override
    public Optional<Autorisation> findAutorisationById(Long id) {
        return autorisationRepository.findById(id);
    }

    @Override
    public Optional<Autorisation> findAutorisationByProfil(Profil profil) {
        return autorisationRepository.findAutorisationByProfil(profil);
    }

    @Override
    public Autorisation saveAutorisation(Autorisation autorisation) {
        return autorisationRepository.save(autorisation);
    }

    @Override
    public List<Autorisation> findAllByProfilId(Long autorisationId) {
        return autorisationRepository.findAllByProfilId(autorisationId);
    }

    @Override
    public void deleteById(Long autorisationId) {
        autorisationRepository.deleteById(autorisationId);
    }

    @Override
    public void deleteAll() {
        autorisationRepository.deleteAll();
    }

}
