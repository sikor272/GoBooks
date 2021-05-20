package pl.umk.mat.gobooks.authors.dto;

import com.neovisionaries.i18n.CountryCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import pl.umk.mat.gobooks.authors.Author;

@Getter
@ApiModel
public class AuthorResponse {
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String firstName;
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String lastName;
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final CountryCode nationality;
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String biography;
    @ApiModelProperty(notes = "It's exactly what you expect.")
    private final Long id;

    public AuthorResponse(Author author) {
        this.id = author.getId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.nationality = author.getNationality();
        this.biography = author.getBiography();
    }
}
