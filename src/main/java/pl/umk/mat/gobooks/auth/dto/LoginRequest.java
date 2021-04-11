package pl.umk.mat.gobooks.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel
@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Email can not be empty")
    @Email
    @ApiModelProperty(notes = "It is exactly what you expect.", required = true)
    private String email;

    @NotBlank(message = "Password can not be empty")
    @Size(min = 8, max = 32, message = "Password length should be between 8 and 32")
    @ApiModelProperty(notes = "It is exactly what you expect.", required = true)
    private String password;
}