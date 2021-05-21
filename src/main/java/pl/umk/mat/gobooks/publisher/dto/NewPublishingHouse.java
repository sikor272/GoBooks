package pl.umk.mat.gobooks.publisher.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@ApiModel
@Getter
public class NewPublishingHouse {
    @NotBlank(message = "Name of publishing house can not be empty")
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private String name;
}
