package axxentis.intenship.laboratoireapi.repositories;

import axxentis.intenship.laboratoireapi.entities.Image;
import axxentis.intenship.laboratoireapi.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
