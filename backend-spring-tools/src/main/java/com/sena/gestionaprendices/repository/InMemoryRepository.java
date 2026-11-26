package com.sena.gestionaprendices.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class InMemoryRepository<T> {
    private final Map<Long, T> records = new LinkedHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<T> findAll() {
        return new ArrayList<>(records.values());
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(records.get(id));
    }

    public T saveNew(Function<Long, T> withId) {
        long id = nextId.getAndIncrement();
        T saved = withId.apply(id);
        records.put(id, saved);
        return saved;
    }

    public Optional<T> replace(Long id, Function<T, T> withId) {
        T existing = records.get(id);
        if (existing == null) {
            return Optional.empty();
        }
        T updated = withId.apply(existing);
        records.put(id, updated);
        return Optional.of(updated);
    }

    public boolean deleteById(Long id) {
        return records.remove(id) != null;
    }

    public long count() {
        return records.size();
    }
}
