package axxentis.intenship.laboratoireapi.dto.request;



import axxentis.intenship.laboratoireapi.advice.CustumMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class CityDto {

    private Long id;
    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private String libelle;
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long countryId;
}
