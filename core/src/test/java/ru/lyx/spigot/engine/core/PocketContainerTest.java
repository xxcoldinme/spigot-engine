package ru.lyx.spigot.engine.core;

import org.apache.commons.lang3.RandomStringUtils;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;

import java.util.stream.Stream;

public class PocketContainerTest {

    public static void main(String[] args) {
        test_baseCase();
        test_grossCase();
    }

    private static void test_baseCase() {
        PocketContainer<String> container = PocketContainer.empty();
        container.addAll("23rdws", "r32rf2ef", "111fsdfsd", "f1kus");

        System.out.println(container.find(s -> s.contains("1")));
        System.out.println(container);
    }

    private static void test_grossCase() {
        PocketContainer<String> container = PocketContainer.chunkify();
        container.addAll(Stream.generate(() -> RandomStringUtils.randomAlphanumeric(16, 20)).limit(100_000).toArray(String[]::new));

        System.out.println(container.find(s -> s.contains("1")));
        System.out.println(container);
    }
}
