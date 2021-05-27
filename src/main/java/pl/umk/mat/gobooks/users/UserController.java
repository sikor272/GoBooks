package pl.umk.mat.gobooks.users;

import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.umk.mat.gobooks.auth.utils.UserPrincipal;
import pl.umk.mat.gobooks.commons.IterableResponse;
import pl.umk.mat.gobooks.commons.exceptions.BadRequest;
import pl.umk.mat.gobooks.commons.exceptions.ResourceAlreadyExist;
import pl.umk.mat.gobooks.commons.exceptions.Unauthorized;
import pl.umk.mat.gobooks.users.dto.UserResponse;

import java.io.IOException;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "This controller provide logic for users.")
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = ".", tags = {"User Controller"})
    @Authorization("Token")
    public IterableResponse<UserResponse> getAll(
            Pageable pageable,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) throws Unauthorized {
        return new IterableResponse<>(userService.findAll(userPrincipal, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = ".", tags = {"User Controller"})
    @Authorization("Token")
    public UserResponse getById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) throws BadRequest {
        return userService.findById(userPrincipal, id);
    }

    @PatchMapping("/avatar")
    @Operation(summary = ".", tags = {"User Controller"})
    @Authorization("Token")
    public UserResponse updateAvatar(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam MultipartFile file
    ) throws IOException, BadRequest, ResourceAlreadyExist {
        return userService.setUserAvatar(userPrincipal, file);
    }

    @DeleteMapping("/avatar")
    @Operation(summary = ".", tags = {"User Controller"})
    @Authorization("Token")
    public UserResponse deleteAvatar(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return userService.deleteUserAvatar(userPrincipal);
    }

}
