package ru.lyx.spigot.engine.util.math.geometry;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import ru.lyx.spigot.engine.util.math.geometry.graph.GeometryCuboid;
import ru.lyx.spigot.engine.util.math.geometry.graph.GeometryGraph;

@RequiredArgsConstructor
public class GeometryFactory {

    private final Location originLocation;
    private final GeometryProperty property;

    public GeometryGraph createCube() {
        return new GeometryCuboid(originLocation, property);
    }

    public GeometryGraph createLine() {
        return new GeometryCuboid(originLocation, property);
    }
}
