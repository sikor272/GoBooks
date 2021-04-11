package pl.umk.mat.gobooks.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {
        super(message);
    }

    public ResourceNotFound() {
        super();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}