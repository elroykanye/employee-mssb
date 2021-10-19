package axxentis.intenship.laboratoireapi.dto.responses;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ScheduleResponseDto {

    private Long id;
    private String title;
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
    private Long taskId;
    private Boolean scheduleComplete;

}
