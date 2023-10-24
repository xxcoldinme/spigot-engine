package ru.lyx.spigot.engine.util.math.geometry;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import ru.lyx.spigot.engine.util.math.VectorUtil;

@UtilityClass
public class Geometry {

    public GeometryFactory of(Location firstPosition, Location secondPosition, GeometryProperty property) {
        Vector vector = VectorUtil.calcCuboidMid(firstPosition.toVector(), secondPosition.toVector());
        Location origin = vector.toLocation(firstPosition.getWorld());

        return Geometry.of(origin, property);
    }

    public GeometryFactory of(Location middle, GeometryProperty property) {
        return new GeometryFactory(middle, property);
    }
}
