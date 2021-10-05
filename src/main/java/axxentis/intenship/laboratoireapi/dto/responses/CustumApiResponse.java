package axxentis.intenship.laboratoireapi.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Noah
 * Date: 03/08/2021
 * Time: 14:38
 *
 * @mail: krolshelby@gmail.com.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustumApiResponse {
    private List data;
    private Object value;
    private Boolean success = true;
    private Boolean error;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime timestamp;
    private final String cause;
    private final String path;
    private String message;
    private HttpStatus httpStatus;
    private String accessToken;
    private String refreshToken;
    private String description;
    private Date date;
    private int status;
    private String tokenType = "Bearer";

    public CustumApiResponse(String message, int status) {
        this.timestamp = LocalDateTime.now();
        this.value = null;
        this.message = message;
        this.accessToken = null;
        this.refreshToken = null;
        this.httpStatus = null;
        this.cause = null;
        this.path = null;
        responseType(success, false);
        this.description = null;
        this.status = status;
        this.tokenType = null;
    }


    public CustumApiResponse(Object value, String accessToken, String refreshToken, String message, HttpStatus httpStatus) {
        this.timestamp = LocalDateTime.now();
        this.value = value;
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.httpStatus = httpStatus;
        this.cause = null;
        this.path = null;
        responseType(success, false);
        this.description = null;
    }

    public CustumApiResponse(Boolean success, Object value, String cause, String path, String message) {
        this.timestamp = LocalDateTime.now();
        this.value = value;
        responseType(success, true);
        this.cause = cause;
        this.path = path;
        this.message = message;
        this.description = null;
    }

    /**
     * @param boolean_statut
     * @param message
     */
    public CustumApiResponse(Boolean boolean_statut, String message) {
        this.timestamp = LocalDateTime.now();
        this.value = null;
        responseType(boolean_statut, true);
        this.cause = null;
        this.path = null;
        this.tokenType = null;
        this.message = message;
        this.description = null;
    }

    /**
     * @param success
     * @param message
     */
    public CustumApiResponse(Boolean success, String message, int status) {
        this.timestamp = LocalDateTime.now();
        this.value = null;
        responseType(success, true);
        this.cause = null;
        this.path = null;
        this.tokenType = null;
        this.message = message;
        this.description = null;
        this.status = status;
    }

    /**
     * @param success
     * @param message
     */
    public CustumApiResponse(Boolean success, String message, HttpServletRequest request, int status) {
        this.timestamp = LocalDateTime.now();
        this.value = null;
        responseType(success, true);
        this.cause = null;
        this.status = status;
        this.path = request.getServletPath();
        this.tokenType = null;
        this.message = message;
        this.description = null;
    }

    public CustumApiResponse(Boolean success, Object value, String message, HttpStatus httpStatus) {
        this.timestamp = LocalDateTime.now();
        this.value = value;
        responseType(success, true);
        this.cause = null;
        this.path = null;
        this.message = message;
        this.httpStatus = httpStatus;
        this.description = null;
    }

    public CustumApiResponse(Boolean success, List<?> data, String message, HttpStatus httpStatus) {
        this.timestamp = LocalDateTime.now();
        this.data = data;
        responseType(success, true);
        this.cause = null;
        this.path = null;
        this.message = message;
        this.httpStatus = httpStatus;
        this.description = null;
        this.tokenType = null;
    }



    public CustumApiResponse(Object value, String message, int status) {
        this.timestamp = LocalDateTime.now();
        this.value = value;
        this.status = status;
        this.message = message;
        responseType(success, false);
        this.cause = null;
        this.path = null;
        this.httpStatus = null;
        this.description = null;
        this.tokenType = null;
    }

    public CustumApiResponse(List<?> data, String message, int status) {
        this.timestamp = LocalDateTime.now();
        this.data = data;
        responseType(success, false);
        this.cause = null;
        this.path = null;
        this.message = message;
        this.httpStatus = null;
        this.status = status;
        this.description = null;
        this.tokenType = null;
    }

    public CustumApiResponse(int status, Date date, String message, String description) {
        this.timestamp = LocalDateTime.now();
        this.date = date;
        this.data = null;
        responseType(success, false);
        this.cause = null;
        this.path = null;
        this.tokenType = null;
        this.value = null;
        this.message = message;
        this.httpStatus = null;
        this.status = status;
        this.description = description;
    }


    public void responseType(boolean response, boolean active) {
        if (active) {
            if (response) {
                this.success = true;
                this.status = 200;
                this.error = null;
            } else {
                this.error = false;
                this.status = 900;
                this.success = null;
            }
        } else {
            this.success = null;
            this.error = null;
        }

    }
}
