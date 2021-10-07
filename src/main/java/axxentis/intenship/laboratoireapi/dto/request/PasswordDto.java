package axxentis.intenship.laboratoireapi.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@ToString
public class PasswordDto {
    @NotBlank(message = "Veuillez remplir les champs obligatoires ")
    @NotNull(message = "Veuillez remplir les champs obligatoires ")
    private String ancienPassword;

    @NotBlank(message = "Veuillez remplir les champs obligatoires ")
    @NotNull(message = "Veuillez remplir les champs obligatoires ")
    private String nouveauPassword;

    @NotBlank(message = "Veuillez remplir les champs obligatoires ")
    @NotNull(message = "Veuillez remplir les champs obligatoires ")
    private String confirmationNouveauPassword;
}
