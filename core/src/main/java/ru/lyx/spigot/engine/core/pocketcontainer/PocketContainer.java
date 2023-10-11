package ru.lyx.spigot.engine.core.pocketcontainer;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
@EqualsAndHashCode
@AllArgsConstructor
public class PocketContainer<T> implements Serializable {

    private static final int CHUNK_SIZE = Byte.MAX_VALUE;

    public static <T> PocketContainer<T> chunkify() {
        return new PocketContainer<T>(new List[0], true);
    }

    public static <T> PocketContainer<T> empty() {
        return new PocketContainer<T>(new List[1], false);
    }

    public static <T> PocketContainer<T> of(T... elements) {
        return new PocketContainer<T>(new List[]{Arrays.asList(elements)}, false);
    }

    public static <T> PocketContainer<T> of(Collection<T> collection) {
        return new PocketContainer<T>(new List[]{new ArrayList<>(collection)}, false);
    }

    private List<T>[] chunks;

    private final boolean useChunks;

    private List<T> peekChunk(T element) {
        if (!useChunks) {
            return chunks[0];
        }

        for (List<T> chunk : chunks) {
            if (chunk.contains(element)) {
                return chunk;
            }
        }

        return availableChunk();
    }

    private List<T> availableChunk() {
        if (chunks.length == 0) {
            return insertChunk();
        } else {
            if (!useChunks) {
                return chunks[0];
            }

            return Stream.of(chunks)
                    .filter(list -> list.size() < CHUNK_SIZE)
                    .findFirst()
                    .orElseGet(this::insertChunk);
        }
    }

    private List<T> insertChunk() {
        if (!useChunks) {
            return chunks[0];
        }

        ensureSize(1);

        ArrayList<T> arrayList = new ArrayList<>();
        chunks[chunks.length - 1] = arrayList;

        return arrayList;
    }

    private void ensureSize(int length) {
        if (useChunks) {
            chunks = Arrays.copyOfRange(chunks, 0, chunks.length + length);
        }
    }

    public PocketContainer<T> remove(T element) {
        peekChunk(element).remove(element);
        return this;
    }

    public PocketContainer<T> add(T element) {
        availableChunk().add(element);
        return this;
    }

    public PocketContainer<T> addAll(T... array) {
        Stream.of(array).forEach(this::add);
        return this;
    }

    public PocketContainer<T> addAll(PocketContainer<T> other) {
        List<T>[] otherChunks = other.chunks;
        ensureSize(otherChunks.length);

        for (int index = 1; index <= otherChunks.length; index++) {
            chunks[chunks.length - index] = otherChunks[index - 1];
        }

        return this;
    }

    public PocketContainer<T> setAll(T... array) {
        chunks = new List[0];
        return addAll(array);
    }

    public PocketContainer<T> setAll(PocketContainer<T> other) {
        chunks = new List[0];
        return addAll(other);
    }

    public Collection<T> find(@NotNull Predicate<T> predicate) {
        Stream<List<T>> stream = useChunks
                ? Stream.of(chunks).parallel()
                : Stream.of(chunks);

        return stream.flatMap(Collection::stream)
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public Optional<T> findFirst(@NotNull Predicate<T> predicate) {
        Stream<List<T>> stream = useChunks
                ? Stream.of(chunks).parallel()
                : Stream.of(chunks);

        return stream.flatMap(Collection::stream)
                .filter(predicate)
                .findFirst();
    }

    public void forEach(Consumer<T> consumer) {
        Stream<List<T>> stream = useChunks
                ? Stream.of(chunks).parallel()
                : Stream.of(chunks);

        Stream<T> map = stream.flatMap(Collection::stream);

        if (useChunks) {
            map.forEachOrdered(consumer);
        }
        else {
            map.forEach(consumer);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(chunks);
    }
}