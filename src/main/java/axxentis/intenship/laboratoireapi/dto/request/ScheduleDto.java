package axxentis.intenship.laboratoireapi.dto.request;

import axxentis.intenship.laboratoireapi.advice.CustumMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ScheduleDto {
    private Long id;

    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private String title;

    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private String task;

    private String startDate;

    private String finishDate;

    private Long percentageComplete;

    private Boolean status;

    private String observation;

    private Long hrResult;

    private Long supervisorResult;

    private String hrObservation;

    private String supervisorObservation;

    private Boolean scheduleComplete;

    private String assigneeEmail;

    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long employeeId;
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private List<Long> taskId;

}
