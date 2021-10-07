package axxentis.intenship.laboratoireapi.dto.responses;

import axxentis.intenship.laboratoireapi.dto.request.PhoneNumberDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UsernameForgotResponseDto {
    private String username;
    private String email;
    private List<PhoneNumberDto> phoneNumbers;
}
