package pl.umk.mat.gobooks.auth.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.umk.mat.gobooks.auth.ApiLoginEntry;
import pl.umk.mat.gobooks.auth.ApiLoginEntryRepository;
import pl.umk.mat.gobooks.utils.exceptions.ResourceNotFound;
import pl.umk.mat.gobooks.utils.exceptions.TokenExpired;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
    private UserDetailsServiceImpl userDetailsService;
    private ApiLoginEntryRepository apiLoginEntriesRepository;

    private String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Token");
    }

    @Override
    @Transactional
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null) {
            ApiLoginEntry apiLogin = apiLoginEntriesRepository.findByToken(token);
            try {
                if (apiLogin.getExpiredAt().isBefore(Instant.now())) {
                    throw new TokenExpired();
                }
                UserDetails userDetails = userDetailsService.loadUserByUsername(apiLogin.getUser().getEmail());

                SecurityContextHolder.getContext().setAuthentication(new TokenBasedAuthentication(userDetails, token));
            } catch (UsernameNotFoundException e) {
                throw new ResourceNotFound(e.getMessage());
            }
            apiLogin.setExpiredAt(Instant.now().plus(1, ChronoUnit.HOURS));
        }
        filterChain.doFilter(request, response);
    }

}