package pl.umk.mat.gobooks.authors.dto;

import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;
import pl.umk.mat.gobooks.authors.Author;

@Getter
public class AuthorResponse {

    private final String firstName;
    private final String lastName;
    private final CountryCode nationality;
    private final String biography;

    public AuthorResponse(Author author) {
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.nationality = author.getNationality();
        this.biography = author.getBiography();
    }
}
