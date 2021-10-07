package axxentis.intenship.laboratoireapi.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PasswordReinitDto {
    @NotBlank(message = "Veuillez entrer le code reçu")
    @NotNull(message = "Veuillez entrer le code reçu")
    private String codeRecu;

    @NotBlank(message = "Veuillez remplir les champs obligatoires ")
    @NotNull(message = "Veuillez remplir les champs obligatoires ")
    private String nouveauPassword;

    @NotBlank(message = "Veuillez remplir les champs obligatoires ")
    @NotNull(message = "Veuillez remplir les champs obligatoires ")
    private String confirmationNouveauPassword;
}
