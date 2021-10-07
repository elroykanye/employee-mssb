package axxentis.intenship.laboratoireapi.servicesImpl;


import axxentis.intenship.laboratoireapi.entities.Profil;
import axxentis.intenship.laboratoireapi.repositories.ProfilRepository;
import axxentis.intenship.laboratoireapi.services.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class ProfilServiceImpl implements ProfilService {
    @Autowired
    private final ProfilRepository profilRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProfilServiceImpl(ProfilRepository profilRepository) {
        super();
        this.profilRepository = profilRepository;
    }

    @Override
    public Profil addProfil(Profil profil) {
        return profilRepository.save(profil);
    }

    @Override
    public Profil updateProfil(Profil profil) {

        return profilRepository.save(profil);
    }

    @Override
    public List<Profil> findAllProfil() {
        return profilRepository.findAll();
    }

    @Override
    public List<Profil> SearchProfil(String searchKey) {
        return profilRepository.findProfilByLibelleContains(searchKey);
    }

    @Override
    public Boolean existeProfilByLibelle(String libelle) {
        return profilRepository.existsByLibelle(libelle);
    }



    @Override
    public Optional<Profil> findProfilById(Long id) {
        return profilRepository.findById(id);
    }

    @Override
    public Optional<Profil> findProfilByLibelle(String name) {
        return profilRepository.findByLibelle(name);
    }


    @Override
    @Transactional(readOnly = false)
    public void deleteProfil(Long idProfil) {
        profilRepository.deleteById(idProfil);
    }
}

