package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.dto.request.TaskDto;
import org.springframework.http.ResponseEntity;

public interface TaskService {

    ResponseEntity<String> addNewTask(TaskDto taskDto);
}
