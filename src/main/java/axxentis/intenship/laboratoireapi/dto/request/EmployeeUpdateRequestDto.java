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
public class EmployeeUpdateRequestDto {
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
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
    @NotNull(message = CustumMessage.CHAMPS_OBLIGATOIRE_VIDE)
    @Size(min = 1, max = 3, message = CustumMessage.MIN_LIST_OBLIGATOIRE)
    private List<PhoneNumberDto> phoneNumbers;
}
