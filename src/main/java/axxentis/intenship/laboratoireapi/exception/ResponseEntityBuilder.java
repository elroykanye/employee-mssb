package axxentis.intenship.laboratoireapi.exception;

import org.springframework.http.ResponseEntity;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Noah
 * Date: 08/08/2021
 * Time: 07:03
 *
 * @mail: krolshelby21@gmail.com.
 */
public class ResponseEntityBuilder {

    public static ResponseEntity<Object> build(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
