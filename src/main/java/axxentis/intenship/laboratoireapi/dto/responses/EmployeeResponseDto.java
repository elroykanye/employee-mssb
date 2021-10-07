package axxentis.intenship.laboratoireapi.dto.responses;

import axxentis.intenship.laboratoireapi.dto.request.DepartmentDto;
import axxentis.intenship.laboratoireapi.dto.request.LangueDto;
import axxentis.intenship.laboratoireapi.dto.request.PhoneNumberDto;
import axxentis.intenship.laboratoireapi.dto.request.TaskDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class EmployeeResponseDto {
    private Long id;
    private String lastName;
    private String firstName;
    private Boolean statut;
    private String email;
    private String username;
    private Boolean onLine;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime dateLastConnection;
    private Integer nombreGenerationCode;
    private Integer nombreVerifyCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime dateLastVerifyCode;
    private Boolean isSuspended;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime dateFinSuspension;


    private CityResponseDto city;
    private DepartmentResponseDto department;
    private List<PhoneNumberDto> phoneNumbers;
    private List<EmployeeProfilResponseDto> employeeProfils;
    private List<TaskDto> tasks;
}