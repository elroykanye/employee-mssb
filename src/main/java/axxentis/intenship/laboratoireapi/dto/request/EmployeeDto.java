package axxentis.intenship.laboratoireapi.dto.request;


import axxentis.intenship.laboratoireapi.advice.CustumMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
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
    private Long profilId;
    @Null(message = CustumMessage.SELECTIONNER_PRIVILEGE)
    private List<Long> privilegeIds;
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @Size(min = 1, max = 3, message = CustumMessage.MIN_LIST_OBLIGATOIRE)
    private List<PhoneNumberDto> phoneNumbers;

    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long cityId;
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    private Long departmentId;
}
