package axxentis.intenship.laboratoireapi.dto.request;

import axxentis.intenship.laboratoireapi.advice.CustumMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    private Long HR_result;
    private Long supervisor_result;
    private String HR_observation;
    private String supervisor_observation;
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long employeeId;
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long taskId;
    private Boolean scheduleComplete;
    private String assigneeEmail;

}
