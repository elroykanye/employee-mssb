package axxentis.intenship.laboratoireapi.services;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Image;
import axxentis.intenship.laboratoireapi.entities.Privilege;

import java.util.List;
import java.util.Optional;

public interface PrivilegeService {
    public List<Privilege> getAllPrivileges();

    Optional<Privilege> findPrivilegeById(Long id);

    public Privilege addPrivilege(Privilege privilege);

    public Privilege updatePrivilege(Long id, Privilege privilege);

    void deletePrivilege(Long id);

}
