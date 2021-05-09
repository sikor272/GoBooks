package pl.umk.mat.gobooks.authors.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class AuthorBiography {

    @NotEmpty
    private String biography;
}
