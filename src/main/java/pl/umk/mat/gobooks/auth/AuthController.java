package pl.umk.mat.gobooks.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.umk.mat.gobooks.auth.dto.AuthResponse;
import pl.umk.mat.gobooks.auth.dto.LoginRequest;
import pl.umk.mat.gobooks.auth.dto.RegisterRequest;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Controller", description = "This controller provide login/register logic.")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Login with password.", tags = {"Authentication Controller"})
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        return authService.login(loginRequest);
    }

    @Operation(summary = "Register with password.", tags = {"Authentication Controller"})
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse register(
            @RequestBody @Valid RegisterRequest registerRequest
    ) {
        return authService.register(registerRequest);
    }

}
