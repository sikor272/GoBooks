package pl.umk.mat.gobooks.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class TokenExpired extends RuntimeException {
    public TokenExpired(String msg) {
        super(msg);
    }

    public TokenExpired() {
        super();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}