package it.ziopagnotta.api.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public final class Property<T> {
    private final T value;
}
