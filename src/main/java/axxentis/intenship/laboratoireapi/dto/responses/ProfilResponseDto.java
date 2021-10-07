package axxentis.intenship.laboratoireapi.dto.responses;


import axxentis.intenship.laboratoireapi.dto.request.AutorisationDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProfilResponseDto {
    private Long id;
    private String libelle;
    private Boolean isInitialized;
    private List<AutorisationDto> autorisations;

}
