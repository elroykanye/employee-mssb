package axxentis.intenship.laboratoireapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Noah
 * Date: 25/08/2021
 * Time: 09:31
 *
 * @mail: krolshelby21@gmail.com.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends  RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ConflictException(String message) {
        super(message);
    }
}
