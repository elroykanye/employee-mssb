package axxentis.intenship.laboratoireapi.mapper;

import axxentis.intenship.laboratoireapi.dto.request.ScheduleDto;
import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Schedule;
import axxentis.intenship.laboratoireapi.entities.Task;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.impl")
public interface ScheduleMapper {


    @Mapping(target = "taskId", expression = "java(mapTaskId(schedule.getTasks()))")
    @Mapping(target = "employeeId", expression = "java(mapEmployeeId(schedule.getEmployee()))")
    @Mapping(target = "assigneeEmail", expression = "java(mapAssigneeEmail(schedule.getEmployee()))")
    ScheduleDto mapScheduleToDto(Schedule schedule);

    // TODO make sure to handle ignored mappings where necessary
    @InheritInverseConfiguration
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    Schedule mapDtoToSchedule(ScheduleDto scheduleDto);

    // 1. mapper expressions
    default String mapAssigneeEmail(Employee employee) {
        return employee.getEmail();
    }

    default Long mapEmployeeId(Employee employee) {
        return employee.getEmployeeId();
    }

    default List<Long> mapTaskId(List<Task> tasks) {
        return tasks.stream()
                .map(Task::getId)
                .collect(Collectors.toList());
    }

    // 2. inverse mapper functions

}
