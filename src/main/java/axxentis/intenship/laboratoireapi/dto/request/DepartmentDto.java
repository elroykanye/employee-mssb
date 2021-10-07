package axxentis.intenship.laboratoireapi.dto.request;

import axxentis.intenship.laboratoireapi.advice.CustumMessage;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DepartmentDto {
    private Long id;
    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private String libelle;
    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private String description;
}
