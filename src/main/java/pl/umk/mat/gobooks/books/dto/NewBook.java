package pl.umk.mat.gobooks.books.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@ApiModel
public class NewBook {

    @NotBlank(message = "ISBN of book cannot be empty")
    @ISBN(message = "ISBN must be in correct format")
    private String isbn;
    @NotBlank(message = "Title of book cannot be empty")
    private String title;
    @NotBlank(message = "Category cannot be empty")
    private String category;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
    @NotNull(message = "Book must have an author")
    private Long authorId;
    @NotNull(message = "Book must have an publisher")
    private Long publisherId;
}
