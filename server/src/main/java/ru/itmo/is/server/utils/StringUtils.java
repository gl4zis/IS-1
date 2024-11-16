package ru.itmo.is.server.utils;

import java.util.Collection;
import java.util.stream.Collectors;

public class StringUtils {
    private StringUtils() { }

    public static String prettyString(Collection<?> c) {
        return String.format("[%s]", c.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }
}
