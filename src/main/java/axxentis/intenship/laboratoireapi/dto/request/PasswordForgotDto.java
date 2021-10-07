package axxentis.intenship.laboratoireapi.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class PasswordForgotDto {
    @NotBlank(message = "Veuillez remplir les champs obligatoires")
    @NotNull(message = "Veuillez remplir les champs obligatoires")
    private String username;
    @NotBlank(message = "Veuillez preciser une option de récupération par SMS ou par Email")
    @NotNull(message = "Veuillez preciser une option de récupération par SMS ou par Email")
    private String optionRecuperation;
    private String phoneNumber;
    private String email;

}
