package axxentis.intenship.laboratoireapi.mapper;

import axxentis.intenship.laboratoireapi.dto.request.TaskDto;
import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Task;
import lombok.AllArgsConstructor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.impl")
public interface TaskMapper {

    @Mapping(target = "assigneeEmail", expression = "java(mapEmployee(task.getEmployee()))")
    @Mapping(target = "taskComplete", expression = "java(mapTaskComplete(task.getTaskComplete()))")
    TaskDto mapTaskToDto(Task task);

    @InheritInverseConfiguration
    @Mapping(target = "employee", ignore = true)
    Task mapDtoToTask(TaskDto taskDto);

    default String mapEmployee(Employee employee) {
        return employee.getEmail();
    }

    default Boolean mapTaskComplete(Boolean taskComplete) {
        return taskComplete != null && taskComplete;
    }


}
