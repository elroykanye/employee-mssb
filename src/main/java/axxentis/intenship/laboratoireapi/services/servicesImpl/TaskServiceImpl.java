package axxentis.intenship.laboratoireapi.services.servicesImpl;

import axxentis.intenship.laboratoireapi.dto.request.TaskDto;
import axxentis.intenship.laboratoireapi.entities.Schedule;
import axxentis.intenship.laboratoireapi.entities.Task;
import axxentis.intenship.laboratoireapi.mapper.TaskMapper;
import axxentis.intenship.laboratoireapi.repositories.ScheduleRepository;
import axxentis.intenship.laboratoireapi.repositories.TaskRepository;
import axxentis.intenship.laboratoireapi.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final ScheduleRepository scheduleRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public ResponseEntity<String> addNewTask(TaskDto taskDto) {
        AtomicBoolean taskAdded = new AtomicBoolean(false);
        Task task = taskMapper.mapDtoToTask(taskDto);

        Optional<Schedule> scheduleOptional = scheduleRepository.findScheduleByTitle(taskDto.getAssigneeEmail());
        scheduleOptional.ifPresentOrElse(
                schedule -> {
                    // set employee to the task
                    task.setSchedule(schedule);

                    // save the repository with the employee gotten
                    taskRepository.save(task);

                    // task added flag success
                    taskAdded.set(true);
                },
                () -> {}
        );

        return taskAdded.get() ?
                new ResponseEntity<>("Task added", HttpStatus.CREATED):
                new ResponseEntity<>("Schedule not found", HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> taskDtos = taskRepository.findAll()
                .stream()
                .map(taskMapper::mapTaskToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(taskDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TaskDto>> getAllTasksBySchedule(String scheduleTitle) {
        AtomicBoolean tasksFound = new AtomicBoolean(false);
        AtomicReference<List<Task>> schduledTasks = new AtomicReference<>();

        Optional<Schedule> scheduleOptional = scheduleRepository.findScheduleByTitle(scheduleTitle);
        scheduleOptional.ifPresentOrElse(
                schedule -> {
                    schduledTasks.set(taskRepository.findAllBySchedule(schedule));
                    tasksFound.set(true);
                },
                () -> {}
        );

        List<TaskDto> taskDtos = schduledTasks.get()
                .stream()
                .map(taskMapper::mapTaskToDto)
                .collect(Collectors.toList());

        return tasksFound.get() ?
                new ResponseEntity<>(taskDtos, HttpStatus.FOUND):
                new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
    }

//    @Override
//    public ResponseEntity<List<TaskDto>> getAllTasksByEmployee(String employeeEmail) {
//        AtomicBoolean tasksFound = new AtomicBoolean(false);
//        AtomicReference<List<Task>> employeeTasks = new AtomicReference<>();
//
//        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(employeeEmail);
//        employeeOptional.ifPresentOrElse(
//                employee -> {
//                    employeeTasks.set(taskRepository.findAllByEmployee(employee));
//                    tasksFound.set(true);
//                },
//                () -> {}
//        );
//
//        List<TaskDto> taskDtos = employeeTasks.get()
//                .stream()
//                .map(taskMapper::mapTaskToDto)
//                .collect(Collectors.toList());
//
//        return tasksFound.get() ?
//                new ResponseEntity<>(taskDtos, HttpStatus.FOUND):
//                new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
//    }

    @Override
    public ResponseEntity<String> updateTask(TaskDto taskDto) {
        AtomicBoolean taskUpdated = new AtomicBoolean(false);
        AtomicReference<String> updateMessage = new AtomicReference<>("Default message");
        Optional<Task> taskOptional = taskRepository.findById(taskDto.getId());

        taskOptional.ifPresentOrElse(
                task -> {
                    Task updatedTask = taskMapper.mapDtoToTask(taskDto);
                    taskRepository.save(updatedTask);
                    taskUpdated.set(true);
                    updateMessage.set("Task updated");
                },
                () -> updateMessage.set("Task does not exist")
        );
        return taskUpdated.get() ?
                new ResponseEntity<>(updateMessage.get(), HttpStatus.ACCEPTED):
                new ResponseEntity<>(updateMessage.get(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> deleteTask(Long taskId) {
        AtomicBoolean taskDeleted = new AtomicBoolean(false);

        Optional<Task> taskOptional = taskRepository.findById(taskId);

        taskOptional.ifPresentOrElse(
                task -> {
                    taskRepository.deleteById(taskId);
                    taskDeleted.set(true);
                },
                () -> {}
        );

        return taskDeleted.get() ?
                new ResponseEntity<>("Task deleted", HttpStatus.FOUND):
                new ResponseEntity<>("Task not deleted", HttpStatus.NOT_FOUND);
    }
}
