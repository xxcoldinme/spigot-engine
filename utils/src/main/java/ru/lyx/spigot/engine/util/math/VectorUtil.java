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
}
