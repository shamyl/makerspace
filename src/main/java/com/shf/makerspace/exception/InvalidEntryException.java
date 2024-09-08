package com.shf.makerspace.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;


public class InvalidEntryException extends RuntimeException {

    public InvalidEntryException(Class clazz, String... searchParamsMap) {
        super(generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    public InvalidEntryException(String message) {
        super(message);
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return entity +
                " invalid entries " +
                searchParams;
    }

    private static <K, V> Map<K, V> toMap(
            Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Invalid entries");
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }

}
