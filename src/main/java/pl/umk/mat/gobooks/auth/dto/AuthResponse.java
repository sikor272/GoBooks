package pl.umk.mat.gobooks.auth.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import pl.umk.mat.gobooks.auth.enums.Role;
import pl.umk.mat.gobooks.users.User;

@ApiModel
@Getter
public class AuthResponse {
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final Long id;

    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String token;

    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String email;

    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String username;

    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final Role role;

    public AuthResponse(User user, String token) {
        this.id = user.getId();
        this.token = token;
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}