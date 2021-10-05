package axxentis.intenship.laboratoireapi.dto.responses;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Noah
 * Date: 03/08/2021
 * Time: 19:09
 *
 * @mail: krolshelby21@gmail.com.
 */
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
