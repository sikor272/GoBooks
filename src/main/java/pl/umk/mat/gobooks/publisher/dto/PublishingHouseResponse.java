package pl.umk.mat.gobooks.publisher.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import pl.umk.mat.gobooks.publisher.PublishingHouse;

@Getter
@ApiModel
public class PublishingHouseResponse {
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String name;
    @ApiModelProperty(notes = "It's exactly what you expect.")
    private final Long id;

    public PublishingHouseResponse(PublishingHouse publishingHouse) {
        this.id = publishingHouse.getId();
        this.name = publishingHouse.getName();
    }
}
