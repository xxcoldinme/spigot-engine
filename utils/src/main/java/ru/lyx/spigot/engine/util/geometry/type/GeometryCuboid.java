package ru.lyx.spigot.engine.util.geometry.type;

import org.bukkit.util.Vector;
import ru.lyx.spigot.engine.util.geometry.GeometryGraphProperty;

import java.util.HashSet;
import java.util.Set;

public class GeometryCuboid extends AbstractGeometryGraph {

    public GeometryCuboid(GeometryGraphProperty property) {
        super(property);
    }

    @Override
    protected Set<Vector> createOffsets(GeometryGraphProperty property) {
        Set<Vector> offsets = new HashSet<>();

        double spacing = property.getPointSpacing().getSpace();

        double rotateX = Math.toRadians(property.getRotation().getX());
        double rotateY = Math.toRadians(property.getRotation().getY());

        double sizeX = property.getSize().getX();
        double sizeY = property.getSize().getY();
        double sizeZ = property.getSize().getZ();

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
