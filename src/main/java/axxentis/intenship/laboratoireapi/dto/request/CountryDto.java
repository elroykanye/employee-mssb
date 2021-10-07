package axxentis.intenship.laboratoireapi.dto.request;



import axxentis.intenship.laboratoireapi.advice.CustumMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;



@Getter
@Setter
public class CountryDto {

    private Long id;

    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private String indicative;

    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private String isoCode;

    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private String libelle;

}
