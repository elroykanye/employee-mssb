package axxentis.intenship.laboratoireapi.dto.responses;


import axxentis.intenship.laboratoireapi.dto.request.CountryDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityResponseDto {
    private Long id;
    private String libelle;
    private CountryDto country;
}
