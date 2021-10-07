package axxentis.intenship.laboratoireapi.dto.request;

import axxentis.intenship.laboratoireapi.advice.CustumMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class LoginDto {
    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    @NotNull(message = "Le nom d'utilisateur ne peut pas être null")
    private String username;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @NotNull(message = "Le mot de passe ne peut pas être null")
    @Size(min = 6, max = 40, message = CustumMessage.TAILLE_MOT_PASSE_INCORRECTE)
    private String password;
}

