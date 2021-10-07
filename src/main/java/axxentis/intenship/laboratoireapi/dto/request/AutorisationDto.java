package axxentis.intenship.laboratoireapi.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AutorisationDto {
    private Long id;
    private Boolean statut;
    private PrivilegeDto privilege;
}
