package axxentis.intenship.laboratoireapi.services.servicesImpl;

import axxentis.intenship.laboratoireapi.dto.request.TaskDto;
import axxentis.intenship.laboratoireapi.entities.Employee;
import axxentis.intenship.laboratoireapi.entities.Task;
import axxentis.intenship.laboratoireapi.mapper.TaskMapper;
import axxentis.intenship.laboratoireapi.repositories.EmployeeRepository;
import axxentis.intenship.laboratoireapi.repositories.TaskRepository;
import axxentis.intenship.laboratoireapi.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public ResponseEntity<String> addNewTask(TaskDto taskDto) {
        AtomicBoolean taskAdded = new AtomicBoolean(false);
        Task task = taskMapper.mapDtoToTask(taskDto);

        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(taskDto.getAssigneeEmail());
        employeeOptional.ifPresentOrElse(
                employee -> {
                    // set employee to the task
                    task.setEmployee(employee);

                    // save the repository with the employee gotten
                    taskRepository.save(task);

                    // task added flag success
                    taskAdded.set(true);
                },
                () -> {}
        );

        return taskAdded.get() ?
                new ResponseEntity<>("Task added", HttpStatus.CREATED):
                new ResponseEntity<>("Employee not found", HttpStatus.FORBIDDEN);
    }
}
