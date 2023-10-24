package ru.lyx.spigot.engine.util.math.geometry.graph;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import ru.lyx.spigot.engine.util.math.geometry.GeometryProperty;

import java.util.Set;

public class GeometryCuboid extends GeometryGraphAdapter {

    public GeometryCuboid(Location middle, GeometryProperty property) {
        super(middle, property);
    }

    @Override
    public Set<Vector> initOffsets(GeometryProperty property) {
        Set<Vector> offsets = newOffsetsSet();

        double spacing = property.getPointsSpace();

        double rotateX = Math.toRadians(property.getRotationX());
        double rotateY = Math.toRadians(property.getRotationY());

        double sizeX = property.getSizeX();
        double sizeY = property.getSizeY();
        double sizeZ = property.getSizeZ();

        for (double x = -sizeX / 2.0; x <= sizeX / 2.0; x += spacing) {
            for (double y = -sizeY / 2.0; y <= sizeY / 2.0; y += spacing) {
                for (double z = -sizeZ / 2.0; z <= sizeZ / 2.0; z += spacing) {

                    double rotatedX = x * Math.cos(rotateY) - z * Math.sin(rotateY);
                    double rotatedZ = x * Math.sin(rotateY) + z * Math.cos(rotateY);
                    double rotatedY = y * Math.cos(rotateX) - rotatedZ * Math.sin(rotateX);

                    offsets.add(new Vector(rotatedX, rotatedY,
                            y * Math.sin(rotateX) + rotatedZ * Math.cos(rotateX)));
                }
            }
        }

        return offsets;
    }
}
