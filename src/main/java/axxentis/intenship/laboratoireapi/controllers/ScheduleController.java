package axxentis.intenship.laboratoireapi.controllers;

import axxentis.intenship.laboratoireapi.advice.CustumMessage;
import axxentis.intenship.laboratoireapi.advice.CustumStatus;
import axxentis.intenship.laboratoireapi.dto.request.*;
import axxentis.intenship.laboratoireapi.dto.responses.CustumApiResponse;
import axxentis.intenship.laboratoireapi.entities.Schedule;
import axxentis.intenship.laboratoireapi.services.ScheduleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/schedule")
public class ScheduleController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<String> addSchedule(@RequestBody ScheduleDto scheduleDto) {
        return scheduleService.addNewSchedule(scheduleDto);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDto>> getAllTasks() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping(value = "/{employeeEmail}")
    public ResponseEntity<List<ScheduleDto>> getAllSchedulesByEmployee(@PathVariable(value = "employeeEmail") String employeeEmail) {
        return scheduleService.getAllSchedulesByEmployee(employeeEmail);
    }

    @PutMapping
    public ResponseEntity<String> updateSchedule(@RequestBody ScheduleDto scheduleDto) {
        return scheduleService.updateSchedule(scheduleDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSchedule(@RequestParam(name = "schedule_id") Long scheduleId) {
        return scheduleService.deleteSchedule(scheduleId);
    }

    @PostMapping(value = "/schedules/criteria")
    public ResponseEntity<?> getAllScheduleByCriteria(@Valid @RequestBody ScheduleCriteriaDto scheduleCriteriaDto) {
        LOGGER.trace("entering getAllScheduleByCriteria() method");
        String message;
        List<Schedule> schedules = scheduleService.searchByLikeCriteria(scheduleCriteriaDto.getSearch(), scheduleCriteriaDto.getTitle(), scheduleCriteriaDto.getStatus(), scheduleCriteriaDto.getTask());
        System.out.println("Size of date: " + schedules.size());
        if (schedules.size() > 0) {
            List<ScheduleDto> theSchedules = mapList(schedules, ScheduleDto.class);
            message = CustumMessage.OPERATION_SUCCESS;
            LOGGER.info(message);
            return ResponseEntity.ok(new CustumApiResponse(theSchedules, message, CustumStatus.OK));
        } else {
            message = CustumMessage.LISTE_VIDE;
            LOGGER.info(message);
            return ResponseEntity.ok(new CustumApiResponse(new ArrayList<>(), message, CustumStatus.NO_CONTENT));
        }
    }


    /**
     * Transforme source List to dto list targetClass
     *
     * @param source
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        ModelMapper modelMapper = new ModelMapper();
        return source.stream().map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
    }

    /**
     * Transform a Schedule to ScheduleDTO
     *
     * @param schedule
     * @return
     */
    private ScheduleDto mapScheduleToScheduleDto(Schedule schedule) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(schedule, ScheduleDto.class);
    }

}
