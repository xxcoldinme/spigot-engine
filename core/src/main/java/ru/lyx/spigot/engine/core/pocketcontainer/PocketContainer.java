package ru.lyx.spigot.engine.core.pocketcontainer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class PocketContainer<T> {

    public static <T> PocketContainer<T> empty() {
        return new PocketContainer<>(new ArrayList<>());
    }

    public static <T> PocketContainer<T> of(T... elements) {
        return new PocketContainer<>(Arrays.asList(elements));
    }

    public static <T> PocketContainer<T> of(Collection<T> collection) {
        return new PocketContainer<>(new ArrayList<>(collection));
    }

    private final List<T> elements;

    public PocketContainer<T> remove(T element) {
        elements.remove(element);
        return this;
    }

    public PocketContainer<T> add(T element) {
        elements.add(element);
        return this;
    }

    public PocketContainer<T> addAll(T... array) {
        Stream.of(array).forEach(this::add);
        return this;
    }

    public PocketContainer<T> addAll(PocketContainer<T> other) {
        other.elements.forEach(this::add);
        return this;
    }

    public PocketContainer<T> setAll(T... array) {
        elements.clear();
        return addAll(array);
    }

    public PocketContainer<T> setAll(PocketContainer<T> other) {
        elements.clear();
        return addAll(other);
    }

    public Collection<T> find(@NotNull Predicate<T> predicate) {
        return elements.stream().filter(predicate).collect(Collectors.toList());
    }

    public Optional<T> findFirst(@NotNull Predicate<T> predicate) {
        return elements.stream().filter(predicate).findFirst();
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}