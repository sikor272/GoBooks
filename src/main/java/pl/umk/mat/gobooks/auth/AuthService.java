package pl.umk.mat.gobooks.auth;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.umk.mat.gobooks.auth.dto.AuthResponse;
import pl.umk.mat.gobooks.auth.dto.LoginRequest;
import pl.umk.mat.gobooks.auth.dto.RegisterRequest;
import pl.umk.mat.gobooks.auth.utils.UserPrincipal;
import pl.umk.mat.gobooks.users.User;
import pl.umk.mat.gobooks.users.UserRepository;
import pl.umk.mat.gobooks.utils.exceptions.ResourceAlreadyExist;
import pl.umk.mat.gobooks.utils.exceptions.Unauthorized;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ApiLoginEntryRepository apiLoginEntryRepository;

    public AuthResponse login(LoginRequest loginRequest) {
        try {
            UserPrincipal user = (UserPrincipal) authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )).getPrincipal();
            String token = RandomString.make(50);
            apiLoginEntryRepository.save(new ApiLoginEntry(
                    user.getUser(),
                    token,
                    Instant.now().plus(1, ChronoUnit.HOURS)));
            return new AuthResponse(user.getUser(), token);
        } catch (Exception exception) {
            throw new Unauthorized("Invalid email or password");
        }
    }

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsUserByEmail(registerRequest.getEmail()).equals(Boolean.TRUE)) {
            throw new ResourceAlreadyExist("User with this email already exist");
        }
        if (userRepository.existsUserByUsername(registerRequest.getUsername()).equals(Boolean.TRUE)) {
            throw new ResourceAlreadyExist("User with this username already exist");
        }
        User user = userRepository.save(new User(
                registerRequest.getUsername(),
                passwordEncoder.encode(registerRequest.getPassword()),
                registerRequest.getEmail()
        ));
        String token = RandomString.make(50);
        apiLoginEntryRepository.save(new ApiLoginEntry(
                user,
                token,
                Instant.now().plus(1, ChronoUnit.HOURS)));
        return new AuthResponse(user, token);
    }
}
