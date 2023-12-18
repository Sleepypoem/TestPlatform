package com.sleepypoem.testplatform.testutils.factories.abstracts;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface SimpleFactory<T> {

    T create();

    default List<T> createList(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> create())
                .collect(Collectors.toList());
    }
}
