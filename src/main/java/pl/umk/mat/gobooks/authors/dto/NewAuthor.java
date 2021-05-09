package pl.umk.mat.gobooks.authors.dto;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class NewAuthor {

    private String firstName;
    private String lastName;
    private String nationality;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}
