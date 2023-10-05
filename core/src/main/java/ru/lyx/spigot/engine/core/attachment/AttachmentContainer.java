package ru.lyx.spigot.engine.core.attachment;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class AttachmentContainer<T> {

    public static <T> AttachmentContainer<T> empty() {
        return new AttachmentContainer<>(new ArrayList<>());
    }

    public static <T> AttachmentContainer<T> of(T... definitions) {
        return new AttachmentContainer<>(Arrays.asList(definitions));
    }

    public static <T> AttachmentContainer<T> of(Collection<T> collection) {
        return new AttachmentContainer<>(new ArrayList<>(collection));
    }

    private final List<T> definitions;

    public AttachmentContainer<T> remove(T element) {
        definitions.remove(element);
        return this;
    }

    public AttachmentContainer<T> add(T element) {
        definitions.add(element);
        return this;
    }

    public AttachmentContainer<T> addAll(T... array) {
        Stream.of(array).forEach(this::add);
        return this;
    }

    public AttachmentContainer<T> addAll(AttachmentContainer<T> attachments) {
        attachments.definitions.forEach(this::add);
        return this;
    }

    public AttachmentContainer<T> setAll(T... array) {
        definitions.clear();
        return addAll(array);
    }

    public AttachmentContainer<T> setAll(AttachmentContainer<T> attachments) {
        definitions.clear();
        return addAll(attachments);
    }

    public Collection<T> find(@NotNull Predicate<T> predicate) {
        return definitions.stream().filter(predicate).collect(Collectors.toList());
    }

    public Optional<T> findFirst(@NotNull Predicate<T> predicate) {
        return definitions.stream().filter(predicate).findFirst();
    }
}