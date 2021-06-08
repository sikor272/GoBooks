package pl.umk.mat.gobooks.publisher.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@ApiModel
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewPublishingHouse {
    @NotBlank(message = "Name of publishing house can not be empty")
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private String name;
}
