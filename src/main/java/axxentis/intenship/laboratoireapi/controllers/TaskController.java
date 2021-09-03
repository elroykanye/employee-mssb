package axxentis.intenship.laboratoireapi.controllers;

import axxentis.intenship.laboratoireapi.entities.Department;
import axxentis.intenship.laboratoireapi.entities.Task;
import axxentis.intenship.laboratoireapi.payload.responses.ApiResponse;
import axxentis.intenship.laboratoireapi.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    //Get all tasks
    @GetMapping("/all")
    public ResponseEntity<?> getAllTasks(){
        List<Task> tasks = taskService.getTasks();
        if (!CollectionUtils.isEmpty(tasks)){
            return ResponseEntity.ok(new ApiResponse(true, tasks, "Get task succesfully!", HttpStatus.OK));
        }else {
            return ResponseEntity.ok(new ApiResponse(true, "the List is empty", HttpStatus.NOT_FOUND));
        }
    }

    // Get Tasks by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable("id") final Long id){
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()){
            return ResponseEntity.ok(new ApiResponse(true, task, "Get task suscessfully", HttpStatus.OK));
        } else {
            return ResponseEntity.ok(new ApiResponse(false, "The liste is empty", HttpStatus.NO_CONTENT));
        }
    }

    // Create Task
    @PostMapping("/add")
    public ResponseEntity<?>createTask(@RequestBody Task task){
        Task saveTask = taskService.saveTask(task);
        return ResponseEntity.ok(new ApiResponse(true, saveTask, "Save succesfully", HttpStatus.OK));
    }

    // Update Task
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") final Long id, @RequestBody Task task){
        Optional<Task> t =taskService.getTaskById(id);
        if (t.isPresent()){
            Task currentTask = t.get();

            String title = task.getTitle();

            if (!title.isEmpty() || !title.isBlank()){
                currentTask.setTitle(title);
            }

            String description  = task.getDescription();
            if (!description.isEmpty() || !description.isBlank()){
                currentTask.setDescription(description);
            }

            return ResponseEntity.ok(new ApiResponse(true, taskService.saveTask(currentTask), "Task updated succesfully", HttpStatus.OK));

        }else {
            return ResponseEntity.ok(new ApiResponse(false, "Task not found", HttpStatus.NO_CONTENT));
        }
    }

    // Delete Country by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") final Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok(new ApiResponse(true, "Task deleted succesfully", HttpStatus.OK));
    }


}
