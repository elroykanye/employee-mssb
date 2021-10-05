package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.entities.Privilege;

import java.util.List;
import java.util.Optional;



public interface PrivilegeService {
    Privilege addPrivilege(Privilege privilege);
    Privilege updatePrivilege(Privilege privilege);
    List<Privilege> findAllPrivilege();
    List<Privilege> SearchPrivilege(String searchKey);
    Boolean existePrivilegeByLibelle(String libelle);
    Optional<Privilege> findPrivilegeById(Long id);
    Optional<Privilege> findPrivilegeByLibelle(String libelle);
    void deletePrivilege(Long idPrivilege);
}
