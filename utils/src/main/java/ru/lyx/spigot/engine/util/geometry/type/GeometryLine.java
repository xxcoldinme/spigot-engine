package ru.lyx.spigot.engine.util.geometry.type;

import org.bukkit.util.Vector;
import ru.lyx.spigot.engine.util.geometry.GeometryGraphProperty;

import java.util.HashSet;
import java.util.Set;

public class GeometryLine extends AbstractGeometryGraph {

    public GeometryLine(GeometryGraphProperty property) {
        super(property);
    }

    @Override
    protected Set<Vector> createOffsets(GeometryGraphProperty property) {
        Set<Vector> offsets = new HashSet<>();

        double spacing = property.getPointSpacing().getSpace();

        double cosX = Math.cos(property.getRotation().getX());
        double sinX = Math.sin(property.getRotation().getX());
        double cosY = Math.cos(property.getRotation().getY());
        double sinY = Math.sin(property.getRotation().getY());

        for (double x = 0, y = 0, z = 0;
             x < spacing * property.getLength().getValue();
             x += spacing, y += spacing, z += spacing) {

            double rotatedX = cosY * (cosX * x - sinX * z) - sinY * y;
            double rotatedY = sinX * x + cosX * z;
            double rotatedZ = sinY * (cosX * x - sinX * z) + cosY * y;

            offsets.add(new Vector(rotatedX, rotatedY, rotatedZ));
        }

        return offsets;
    }
}
