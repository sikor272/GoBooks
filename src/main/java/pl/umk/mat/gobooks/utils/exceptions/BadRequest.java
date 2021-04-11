package pl.umk.mat.gobooks.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequest extends RuntimeException {
    public BadRequest(String message) {
        super(message);
    }

    public BadRequest() {
        super();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}