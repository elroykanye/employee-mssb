package axxentis.intenship.laboratoireapi.dto.request;


import axxentis.intenship.laboratoireapi.advice.CustumMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ProfilUpdateDto {
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long id;

    private String libelle;

    @NotNull(message = CustumMessage.SELECTIONNER_TYPE_ORGANISATION)
    private Long typeOrganisationId;
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private List<Long> privileges;

}
