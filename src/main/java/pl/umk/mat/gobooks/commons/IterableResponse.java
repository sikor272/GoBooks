package pl.umk.mat.gobooks.commons;

import lombok.Getter;

@Getter
public class IterableResponse<T> {

    private final Iterable<T> values;

    public IterableResponse(Iterable<T> values) {
        this.values = values;
    }
}
