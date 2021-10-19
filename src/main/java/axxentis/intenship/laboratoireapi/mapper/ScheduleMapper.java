package axxentis.intenship.laboratoireapi.mapper;

import axxentis.intenship.laboratoireapi.dto.request.ScheduleDto;
import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Schedule;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.impl")
public interface ScheduleMapper {
    @Mapping(target = "assigneeEmail", expression = "java(mapEmployee(schedule.getEmployee()))")
    @Mapping(target = "scheduleComplete", expression = "java(mapScheduleComplete(task.getScheduleComplete()))")
    ScheduleDto mapScheduleToDto(Schedule schedule);

    @InheritInverseConfiguration
    @Mapping(target = "employee", ignore = true)
    Schedule mapDtoToSchedule(ScheduleDto scheduleDto);

    default String mapEmployee(Employee employee) {
        return employee.getEmail();
    }

    default Boolean mapScheduleComplete(Boolean scheduleComplete) {
        return scheduleComplete != null && scheduleComplete;
    }

}
