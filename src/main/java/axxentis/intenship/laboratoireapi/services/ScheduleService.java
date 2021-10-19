package axxentis.intenship.laboratoireapi.services;

import axxentis.intenship.laboratoireapi.dto.request.ScheduleDto;
import axxentis.intenship.laboratoireapi.entities.Schedule;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ScheduleService {

    ResponseEntity<String> addNewSchedule(ScheduleDto scheduleDto);

    ResponseEntity<List<ScheduleDto>> getAllSchedules();

    ResponseEntity<List<ScheduleDto>> getAllSchedulesByEmployee(String employeeEmail);

    ResponseEntity<String> updateSchedule(ScheduleDto scheduleDto);

    ResponseEntity<String> deleteSchedule(Long scheduleId);

    List<Schedule> searchByLikeCriteria(String search, String title, Boolean status, String employee);
    List<Schedule> findByCriteria(String title);



}
