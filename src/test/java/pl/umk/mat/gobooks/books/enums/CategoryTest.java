package pl.umk.mat.gobooks.books.enums;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void whenGetCategoryFromExistingName_shouldReturnCorrectCategory() {
        Category thriller = Category.THRILLER;
        Category historicalNovel = Category.HISTORICAL_NOVEL;

        assertEquals(thriller, Category.fromName(thriller.getName()).orElseThrow(IllegalArgumentException::new));
        assertEquals(historicalNovel, Category.fromName(historicalNovel.getName()).orElseThrow(IllegalArgumentException::new));
        assertNotEquals(thriller, Category.fromName(historicalNovel.getName()).orElseThrow(IllegalArgumentException::new));
    }

    @Test
    void whenGetCategoryForNotExistingName_shouldReturnEmptyOptional() {
        assertEquals(Optional.empty(), Category.fromName(""));
    }
}