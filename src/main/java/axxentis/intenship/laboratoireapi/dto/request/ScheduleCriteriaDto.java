package axxentis.intenship.laboratoireapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScheduleCriteriaDto {
    private String search;
    private String title;
    private Boolean status;
    private String task;
}
