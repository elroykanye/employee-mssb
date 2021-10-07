package axxentis.intenship.laboratoireapi.dto.request;


import axxentis.intenship.laboratoireapi.advice.CustumMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
public class SignupDto {
    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @Size(min = 3, max = 20)
    private String username;
    private String lastName;
    private String firstName;

    @Email(message = CustumMessage.EMAIL_INCORRECTE)
    @Size(max = 50)
    private String email;

    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @Size(min = 6, max = 40)
    private String confirmationPassword;

    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @Size(min = 1, max = 3, message = CustumMessage.MIN_LIST_OBLIGATOIRE)
    private List<PhoneNumberDto> phoneNumbers;

    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long cityId;

    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long departementId;

}

