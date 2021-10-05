package axxentis.intenship.laboratoireapi.servicesImpl;


import axxentis.intenship.laboratoireapi.entities.Privilege;
import axxentis.intenship.laboratoireapi.repositories.PrivilegeRepository;
import axxentis.intenship.laboratoireapi.services.PrivilegeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;



@Transactional
@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    private static Log log = LogFactory.getLog(PrivilegeServiceImpl.class);
    private PrivilegeRepository privilegeRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Privilege addPrivilege(Privilege privilege) {
        return  privilegeRepository.save(privilege);
    }

    @Override
    public Privilege updatePrivilege(Privilege privilege) {
        return  privilegeRepository.save(privilege);
    }

    @Override
    public List<Privilege> findAllPrivilege() {
        return privilegeRepository.findAll();
    }

    @Override
    public List<Privilege> SearchPrivilege(String searchKey) {
        return privilegeRepository.findPrivilegeByLibelleContains(searchKey);
    }

    @Override
    public Boolean existePrivilegeByLibelle(String libelle) {
        return privilegeRepository.existsByLibelle(libelle);
    }

    @Override
    public Optional<Privilege> findPrivilegeById(Long id) {
        return privilegeRepository.findById(id);
    }

    @Override
    public Optional<Privilege> findPrivilegeByLibelle(String name) {
        return privilegeRepository.findByLibelle(name);
    }

    @Override
    public void deletePrivilege(Long idPrivilege) {
        privilegeRepository.deleteById(idPrivilege);
    }
}
