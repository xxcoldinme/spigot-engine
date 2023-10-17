package ru.lyx.spigot.engine.util.test.math.geometry;

import ru.lyx.spigot.engine.util.geometry.GeometryGraph;
import ru.lyx.spigot.engine.util.geometry.GeometryGraphProperty;
import ru.lyx.spigot.engine.util.geometry.SpigotGeometryFactory;
import ru.lyx.spigot.engine.util.geometry.property.GeometryPointSpacing;
import ru.lyx.spigot.engine.util.geometry.property.GeometryRotation;
import ru.lyx.spigot.engine.util.geometry.property.GeometrySize;

public class CuboidTest {

    public static void main(String[] args) {
        GeometryGraphProperty graphProperty = GeometryGraphProperty.newBuilder()
                .setRotation(GeometryRotation.of(1f, 2.56f))
                .setPointSpacing(GeometryPointSpacing.of(0.3))
                .setSize(GeometrySize.of(3))
                .build();

        System.out.println(graphProperty);

        GeometryGraph cuboid = SpigotGeometryFactory.createCuboid(graphProperty);
        System.out.println(cuboid.getOffsets());
        System.out.println(cuboid.getOffsets().size());
    }
}
