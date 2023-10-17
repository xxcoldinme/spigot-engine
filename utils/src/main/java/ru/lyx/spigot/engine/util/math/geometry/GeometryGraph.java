package ru.lyx.spigot.engine.util.math.geometry;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Set;
import java.util.function.Consumer;

public interface GeometryGraph {

    GeometryGraphProperty getProperty();

    Set<Vector> getOffsets();

    void apply(Location center, Consumer<Location> pointHandler);
}
