package axxentis.intenship.laboratoireapi.dto.request;


import axxentis.intenship.laboratoireapi.advice.CustumMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class EmployeeUpdatePreferenceRequestDto {
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long id;

    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Boolean activerVolumeNotification;

    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long langueId;
}
