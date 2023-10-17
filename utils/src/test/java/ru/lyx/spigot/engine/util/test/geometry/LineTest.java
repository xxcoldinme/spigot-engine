package ru.lyx.spigot.engine.util.test.geometry;

import ru.lyx.spigot.engine.util.geometry.GeometryGraph;
import ru.lyx.spigot.engine.util.geometry.GeometryGraphProperty;
import ru.lyx.spigot.engine.util.geometry.SpigotGeometryFactory;
import ru.lyx.spigot.engine.util.geometry.property.GeometryLength;
import ru.lyx.spigot.engine.util.geometry.property.GeometryRotation;

public class LineTest {

    public static void main(String[] args) {
        GeometryGraphProperty graphProperty = GeometryGraphProperty.newBuilder()
                .setRotation(GeometryRotation.of(1f, 2.56f))
                .setLength(GeometryLength.of(25))
                .build();

        System.out.println(graphProperty);

        GeometryGraph line = SpigotGeometryFactory.createLine(graphProperty);
        System.out.println(line.getOffsets());
        System.out.println(line.getOffsets().size());
    }
}
