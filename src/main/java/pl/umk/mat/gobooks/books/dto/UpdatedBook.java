package pl.umk.mat.gobooks.books.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@ApiModel
public class UpdatedBook {

    private String title;
    private String category;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;
}
