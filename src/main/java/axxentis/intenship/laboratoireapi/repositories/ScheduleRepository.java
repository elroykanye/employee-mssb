package axxentis.intenship.laboratoireapi.repositories;

import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Schedule;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByEmployee(Employee employee);
    Optional<Schedule> findScheduleByTitle(String title);

    List<Schedule> findAll(Specification<Schedule> scheduleTitle);

}
