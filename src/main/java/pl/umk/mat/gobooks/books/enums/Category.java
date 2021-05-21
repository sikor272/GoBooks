package pl.umk.mat.gobooks.books.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Category {

    FANTASY("fantastyka"),
    SCI_FI("science fiction"),
    HORROR("horror"),
    CRIME("kryminał"),
    THRILLER("thriller"),
    ROMANCE("romans"),
    HISTORICAL_NOVEL("powieść historyczna"),
    ADVENTURE_NOVEL("powieść przygodowa"),
    BIOGRAPHY("biografia"),
    NON_FICTION("literatura faktu"),
    POPULAR_SCIENCE_LITERATURE("literatura popularnonaukowa"),
    CHILDREN_S_LITERATURE("literatura dziecięca"),
    COMICS("komiks"),
    POETRY("poezja"),
    MAGAZINE("czasopismo"),
    ;

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public static Optional<Category> fromName(String name) {
        return Arrays.stream(Category.values())
                .filter(category -> category.name.equals(name))
                .findAny();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
