package axxentis.intenship.laboratoireapi.services.servicesImpl;

import axxentis.intenship.laboratoireapi.dto.request.ScheduleDto;
import axxentis.intenship.laboratoireapi.entities.*;
import axxentis.intenship.laboratoireapi.mapper.ScheduleMapper;
import axxentis.intenship.laboratoireapi.repositories.EmployeeRepository;
import axxentis.intenship.laboratoireapi.repositories.ScheduleRepository;
import axxentis.intenship.laboratoireapi.services.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    public ResponseEntity<String> addNewSchedule(ScheduleDto scheduleDto) {
            AtomicBoolean scheduleAdded = new AtomicBoolean(false);
            Schedule schedule = scheduleMapper.mapDtoToSchedule(scheduleDto);

            Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(scheduleDto.getAssigneeEmail());
            employeeOptional.ifPresentOrElse(
                    employee -> {
                        // set employee to the schedule
                        schedule.setEmployee(employee);

                        // save the repository with the employee gotten
                        scheduleRepository.save(schedule);

                        // task added flag success
                        scheduleAdded.set(true);
                    },
                    () -> {}
            );

            return scheduleAdded.get() ?
                    new ResponseEntity<>("Schedule added", HttpStatus.CREATED):
                    new ResponseEntity<>("Employee not found", HttpStatus.FORBIDDEN);

    }


    @Override
    public ResponseEntity<List<ScheduleDto>> getAllSchedules() {
        List<ScheduleDto> scheduleDtos = scheduleRepository.findAll()
                .stream()
                .map(scheduleMapper::mapScheduleToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(scheduleDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ScheduleDto>> getAllSchedulesByEmployee(String employeeEmail) {
            AtomicBoolean schedulesFound = new AtomicBoolean(false);
            AtomicReference<List<Schedule>> employeeSchedules = new AtomicReference<>();

            Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(employeeEmail);
            employeeOptional.ifPresentOrElse(
                    employee -> {
                        employeeSchedules.set(scheduleRepository.findAllByEmployee(employee));
                        schedulesFound.set(true);
                    },
                    () -> {}
            );

            List<ScheduleDto> scheduleDtos = employeeSchedules.get()
                    .stream()
                    .map(scheduleMapper::mapScheduleToDto)
                    .collect(Collectors.toList());

            return schedulesFound.get() ?
                    new ResponseEntity<>(scheduleDtos, HttpStatus.FOUND):
                    new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
        }


    @Override
    public ResponseEntity<String> updateSchedule(ScheduleDto scheduleDto) {
        AtomicBoolean scheduleUpdated = new AtomicBoolean(false);
        AtomicReference<String> updateMessage = new AtomicReference<>("Default message");
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleDto.getId());

        scheduleOptional.ifPresentOrElse(
                schedule -> {
                    Schedule updatedSchedule = scheduleMapper.mapDtoToSchedule(scheduleDto);
                    scheduleRepository.save(updatedSchedule);
                    scheduleUpdated.set(true);
                    updateMessage.set("Schedule updated");
                },
                () -> updateMessage.set("Schedule does not exist")
        );
        return scheduleUpdated.get() ?
                new ResponseEntity<>(updateMessage.get(), HttpStatus.ACCEPTED):
                new ResponseEntity<>(updateMessage.get(), HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<String> deleteSchedule(Long scheduleId) {
        AtomicBoolean scheduleDeleted = new AtomicBoolean(false);

        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);

        scheduleOptional.ifPresentOrElse(
                schedule -> {
                    scheduleRepository.deleteById(scheduleId);
                    scheduleDeleted.set(true);
                },
                () -> {}
        );

        return scheduleDeleted.get() ?
                new ResponseEntity<>("Schedule deleted", HttpStatus.FOUND):
                new ResponseEntity<>("Schedule not deleted", HttpStatus.NOT_FOUND);

    }

    @Override
    public List<Schedule> findByCriteria(String title) {
        return scheduleRepository.findAll((Specification<Schedule>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (title != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("title"), title)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }

    public List<Schedule> searchByLikeCriteria(String search, String title, Boolean status, String employee) {
        return scheduleRepository.findAll((Specification<Schedule>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Schedule, Employee> employeeJoin = root.join("employee");
            if (search != null) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("title"), "%" + search + "%"),
                        criteriaBuilder.like(root.get("createAt"), "%" + search + "%")
                ));
            }
            if (title != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%"+title.toLowerCase()+"%")));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status)));
            }
            if (employee != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.lower(employeeJoin.get("employee")), employee.toLowerCase())));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }


}
