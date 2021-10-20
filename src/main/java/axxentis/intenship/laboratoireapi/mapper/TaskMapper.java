package axxentis.intenship.laboratoireapi.mapper;

import axxentis.intenship.laboratoireapi.dto.request.TaskDto;
import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Task;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring", implementationPackage = "<PACKAGE_NAME>.impl")
public interface TaskMapper {

    TaskDto mapTaskToDto(Task task);

    @InheritInverseConfiguration
    @Mapping(target = "schedule", ignore = true)
    Task mapDtoToTask(TaskDto taskDto);


}
