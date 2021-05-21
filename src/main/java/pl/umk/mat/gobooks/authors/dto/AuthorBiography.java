package pl.umk.mat.gobooks.authors.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@ApiModel
public class AuthorBiography {
    @NotBlank(message = "Biography can not be empty")
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private String biography;
}
