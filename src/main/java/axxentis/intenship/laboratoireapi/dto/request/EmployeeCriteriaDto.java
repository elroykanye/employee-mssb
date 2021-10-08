package axxentis.intenship.laboratoireapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeCriteriaDto {
    private String search;
    private String lastName;
    private String firstName;
    private Boolean status;
    private String phoneNumber;
    private String department;
}
