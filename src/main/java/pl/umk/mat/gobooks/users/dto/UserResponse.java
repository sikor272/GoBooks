package pl.umk.mat.gobooks.users.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import pl.umk.mat.gobooks.auth.enums.Role;
import pl.umk.mat.gobooks.users.User;

@Getter
@ApiModel
public class UserResponse {
    @ApiModelProperty(notes = "It's exactly what you expect.")
    private final Long id;
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String username;
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String avatar;
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final Role role;


    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.role = user.getRole();
    }
}
