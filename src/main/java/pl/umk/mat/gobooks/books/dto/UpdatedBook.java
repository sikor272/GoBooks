package pl.umk.mat.gobooks.books.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@ApiModel
public class UpdatedBook {

    @NotBlank(message = "Title of book cannot be empty")
    private String title;
    @NotBlank(message = "Category cannot be empty")
    private String category;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
}
