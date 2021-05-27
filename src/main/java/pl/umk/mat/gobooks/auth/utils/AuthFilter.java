package pl.umk.mat.gobooks.auth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import pl.umk.mat.gobooks.auth.ApiLoginEntry;
import pl.umk.mat.gobooks.auth.ApiLoginEntryRepository;
import pl.umk.mat.gobooks.commons.exceptions.Unauthorized;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component

public class AuthFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private final ApiLoginEntryRepository apiLoginEntriesRepository;

    private final HandlerExceptionResolver resolver;

    @Autowired
    public AuthFilter(UserDetailsServiceImpl userDetailsService, ApiLoginEntryRepository apiLoginEntriesRepository, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.userDetailsService = userDetailsService;
        this.apiLoginEntriesRepository = apiLoginEntriesRepository;
        this.resolver = resolver;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Token");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getTokenFromRequest(request);
        if (token != null) {
            try {
                ApiLoginEntry apiLogin = apiLoginEntriesRepository.findByToken(token);
                if (apiLogin.getExpiredAt().isBefore(Instant.now())) {
                    throw new Unauthorized("Token expired");
                }
                var userDetails = userDetailsService.loadUserByUsername(apiLogin.getUser().getEmail());
                SecurityContextHolder.getContext().setAuthentication(new TokenBasedAuthentication(userDetails, token));
                apiLogin.setExpiredAt(Instant.now().plus(1, ChronoUnit.HOURS));
            } catch (Unauthorized e) {
                resolver.resolveException(request, response, null, e);
                return;
            } catch (NullPointerException e) {
                resolver.resolveException(request, response, null, new Unauthorized("Invalid token"));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}