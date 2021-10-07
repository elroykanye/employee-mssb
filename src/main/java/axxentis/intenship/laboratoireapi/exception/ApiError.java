package axxentis.intenship.laboratoireapi.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Noah
 * Date: 08/08/2021
 * Time: 07:02
 *
 * @mail: krolshelby21@gmail.com.
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;

    private HttpStatus status;
    private String message;
    private String error;

}

