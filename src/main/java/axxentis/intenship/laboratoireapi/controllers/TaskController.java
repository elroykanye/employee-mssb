package axxentis.intenship.laboratoireapi.controllers;

import axxentis.intenship.laboratoireapi.dto.request.TaskDto;
import axxentis.intenship.laboratoireapi.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/task")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<String> addTask(@RequestBody TaskDto taskDto) {
        return taskService.addNewTask(taskDto);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping(value = "/{employeeEmail}")
    public ResponseEntity<List<TaskDto>> getAllTasksByEmployee(@PathVariable(value = "employeeEmail") String employeeEmail) {
        return taskService.getAllTasksByEmployee(employeeEmail);
    }

    @PutMapping
    public ResponseEntity<String> updateTask(@RequestBody TaskDto taskDto) {
        return taskService.updateTask(taskDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTask(@RequestParam(name = "task_id") Long taskId) {
        return taskService.deleteTask(taskId);
    }
}
