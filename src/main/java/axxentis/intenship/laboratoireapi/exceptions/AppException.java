package axxentis.intenship.laboratoireapi.exceptions;

import org.springframework.http.HttpStatus;


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

