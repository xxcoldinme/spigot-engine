package ru.lyx.spigot.engine.util.math;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

@UtilityClass
public class VectorUtil {

    public Location applyOffset(Location origin, Vector offset) {
        return origin.clone().add(offset);
    }

    public Location applyOffset(World world, Vector origin, Vector offset) {
        return applyOffset(origin.toLocation(world), offset);
    }

    public Vector getLookDirection(Location from, Location to) {
        return getLookDirection(from.toVector(), to.toVector());
    }

    public Vector getLookDirection(Vector from, Vector to) {
        return to.clone().subtract(from).normalize();
    }

    public Vector calcCuboidMid(Vector from, Vector to) {
        double fromX = from.getX();
        double fromY = from.getY();
        double fromZ = from.getZ();

        double toX = to.getX();
        double toY = to.getY();
        double toZ = to.getZ();

        Vector min = new Vector(
                Math.min(fromX, toX),
                Math.min(fromY, toY),
                Math.min(fromZ, toZ));
        Vector max = new Vector(
                Math.max(fromX, toX),
                Math.max(fromY, toY),
                Math.max(fromZ, toZ));

        return new Vector(
                (max.getX() - min.getX()) / 2,
                (max.getY() - min.getY()) / 2,
                (max.getZ() - min.getZ()) / 2);
    }
}
