package axxentis.intenship.laboratoireapi.serviceImplementations;

import axxentis.intenship.laboratoireapi.entities.Image;
import axxentis.intenship.laboratoireapi.entities.Privilege;
import axxentis.intenship.laboratoireapi.repositories.PrivilegeRepository;
import axxentis.intenship.laboratoireapi.services.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PrivilegeServiceImplementation implements PrivilegeService {

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Override
    public List<Privilege> getAllPrivileges() {
        return privilegeRepository.findAll();
    }

    @Override
    public Optional<Privilege> findPrivilegeById(Long privilegeId) {
        return privilegeRepository.findById(privilegeId);
    }


    @Override
    public Privilege addPrivilege(Privilege privilege) {
        Privilege newPrivilege = new Privilege();
        newPrivilege.setName(privilege.getName());
        return privilegeRepository.save(newPrivilege);

    }

    @Override
    public Privilege updatePrivilege(Long privilegeId, Privilege privilege) {
        Optional<Privilege> privilegeToUpdate = findPrivilegeById(privilegeId);
        privilegeToUpdate.get().setName(privilege.getName());
        return privilegeRepository.save(privilegeToUpdate.get());

    }

    @Override
    public void deletePrivilege(Privilege privilege) {
        privilegeRepository.deleteAll();

    }
}
