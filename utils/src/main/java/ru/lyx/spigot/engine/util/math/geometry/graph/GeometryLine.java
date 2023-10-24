package ru.lyx.spigot.engine.util.math.geometry.graph;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import ru.lyx.spigot.engine.util.math.geometry.GeometryProperty;

import java.util.Set;

public class GeometryLine extends GeometryGraphAdapter {

    public GeometryLine(Location middle, GeometryProperty property) {
        super(middle, property);
    }

    @Override
    public Set<Vector> initOffsets(GeometryProperty property) {
        Set<Vector> offsets = newOffsetsSet();

        double spacing = property.getPointsSpace();

        double cosX = Math.cos(property.getRotationX());
        double sinX = Math.sin(property.getRotationX());

        double cosY = Math.cos(property.getRotationY());
        double sinY = Math.sin(property.getRotationY());

        double length = (Math.max(Math.max(property.getSizeX(), property.getSizeY()),
                property.getSizeZ()) / spacing);

        for (double x = 0, y = 0, z = 0;
             x < spacing * length;
             x += spacing, y += spacing, z += spacing) {

            double rotatedX = cosY * (cosX * x - sinX * z) - sinY * y;
            double rotatedY = sinX * x + cosX * z;
            double rotatedZ = sinY * (cosX * x - sinX * z) + cosY * y;

            offsets.add(new Vector(rotatedX, rotatedY, rotatedZ));
        }

        return offsets;
    }
}
