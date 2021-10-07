package axxentis.intenship.laboratoireapi.dto.responses;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmployeeProfilResponseDto {
    private Long id;
    private ProfilResponseDto profil;
}