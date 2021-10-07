package axxentis.intenship.laboratoireapi.dto.request;

import javax.validation.constraints.NotNull;


public class LogOutDto {

    @NotNull(message = "Veuillez selectionner un utilisateur")
    private Long employeeId;

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}

