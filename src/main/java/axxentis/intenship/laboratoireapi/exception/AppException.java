package axxentis.intenship.laboratoireapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Noah
 * Date: 05/08/2021
 * Time: 16:52
 *
 * @mail: krolshelby21@gmail.com.
 */
public class AppException extends RuntimeException {

    private final HttpStatus status;
    private String title;

    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public AppException(String title, String message, HttpStatus status) {
        super(message);
        this.title = title;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

