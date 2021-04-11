package pl.umk.mat.gobooks.auth.utils;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

@Getter
public class TokenBasedAuthentication extends AbstractAuthenticationToken {
    private final UserDetails userDetails;
    private final String token;

    public TokenBasedAuthentication(UserDetails userDetails, String token) {
        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
        this.token = token;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public UserDetails getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenBasedAuthentication)) return false;
        TokenBasedAuthentication tokenBasedAuthentication = (TokenBasedAuthentication) o;
        return token.equals(tokenBasedAuthentication.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
