package axxentis.intenship.laboratoireapi.services;


import axxentis.intenship.laboratoireapi.dto.request.TaskDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {

    ResponseEntity<String> addNewTask(TaskDto taskDto);

    ResponseEntity<List<TaskDto>> getAllTasks();

    ResponseEntity<List<TaskDto>> getAllTasksBySchedule(String scheduleTitle);

    ResponseEntity<String> updateTask(TaskDto taskDto);

    ResponseEntity<String> deleteTask(Long taskId);
}
