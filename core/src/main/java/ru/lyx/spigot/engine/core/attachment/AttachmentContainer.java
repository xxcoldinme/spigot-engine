package ru.lyx.spigot.engine.core.attachment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
@Getter
@ToString
@RequiredArgsConstructor
public class AttachmentContainer<T> {

    private static final AttachmentContainer<?> EMPTY
            = new AttachmentContainer<>(Collections.emptyList());

    public static <T> AttachmentContainer<T> empty() {
        return (AttachmentContainer<T>) EMPTY;
    }

    public static <T> AttachmentContainer<T> of(T... definitions) {
        return new AttachmentContainer<>(Arrays.asList(definitions));
    }

    private final List<T> definitions;

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

}