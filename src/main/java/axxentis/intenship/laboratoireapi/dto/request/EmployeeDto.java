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
public class EmployeeDto {
    private Long id;
    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private String lastName;
    @NotBlank(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private String firstName;
    @Email(message = CustumMessage.EMAIL_INCORRECTE)
    @Size(max = 50)
    private String email;
    private String username;
    private String password;
    private String confirmationPassword;
    private String serveurId;
    private String fonction;
    private Boolean isPrincipal;
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long langueId;
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long profilId;
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @Size(min = 1, max = 3, message = CustumMessage.MIN_LIST_OBLIGATOIRE)
    private List<PhoneNumberDto> phoneNumbers;
}
