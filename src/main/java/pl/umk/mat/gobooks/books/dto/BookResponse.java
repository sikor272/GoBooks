package pl.umk.mat.gobooks.books.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import pl.umk.mat.gobooks.books.Book;

@Getter
@ApiModel
public class BookResponse {

    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final Long id;
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String title;
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String category;
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String author;
    @ApiModelProperty(notes = "It is exactly what you expect.")
    private final String publisher;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.category = book.getCategory().getName();
        this.author = book.getAuthor().getBasicPersonals();
        this.publisher = book.getPublishingHouse().getName();
    }
}
