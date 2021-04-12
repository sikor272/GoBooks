package pl.umk.mat.gobooks.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class Unauthorized extends RuntimeException {
    public Unauthorized(String message) {
        super(message);
    }

    public Unauthorized() {
        super();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
