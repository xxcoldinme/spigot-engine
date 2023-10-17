package ru.lyx.spigot.engine.util.geometry;

import lombok.experimental.UtilityClass;
import ru.lyx.spigot.engine.util.geometry.type.AbstractGeometryGraph;
import ru.lyx.spigot.engine.util.geometry.type.GeometryCuboid;
import ru.lyx.spigot.engine.util.geometry.type.GeometryLine;

@UtilityClass
public class SpigotGeometryFactory {

    public GeometryGraph createCuboid(GeometryGraphProperty property) {
        return createGraph(new GeometryCuboid(property));
    }

    public GeometryGraph createLine(GeometryGraphProperty property) {
        return createGraph(new GeometryLine(property));
    }

    private GeometryGraph createGraph(AbstractGeometryGraph abstractGeometryGraph) {
        abstractGeometryGraph.init();
        return abstractGeometryGraph;
    }
}
