package ru.lyx.spigot.engine.util.math.geometry;

import lombok.experimental.UtilityClass;
import ru.lyx.spigot.engine.util.math.geometry.type.AbstractGeometryGraph;
import ru.lyx.spigot.engine.util.math.geometry.type.Cuboid;

@UtilityClass
public class GeometryFactory {

    public GeometryGraph createCuboid(GeometryGraphProperty property) {
        return createGraph(new Cuboid(property));
    }

    private GeometryGraph createGraph(AbstractGeometryGraph abstractGeometryGraph) {
        abstractGeometryGraph.init();
        return abstractGeometryGraph;
    }
}
