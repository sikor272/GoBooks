package pl.umk.mat.gobooks.authors.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@ApiModel
public class NewAuthor {
    @NotBlank(message = "First name can not be empty")
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private String firstName;

    @NotBlank(message = "Last name can not be empty")
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private String lastName;

    @NotBlank(message = "Nationality can not be empty")
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private String nationality;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private LocalDate birthDate;
}
