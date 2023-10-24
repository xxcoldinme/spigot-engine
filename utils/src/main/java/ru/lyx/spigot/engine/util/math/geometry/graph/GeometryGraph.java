package ru.lyx.spigot.engine.util.math.geometry.graph;

import org.bukkit.util.Vector;
import ru.lyx.spigot.engine.util.math.geometry.GeometryProperty;
import ru.lyx.spigot.engine.util.math.geometry.GeometryView;

import java.util.Set;

public interface GeometryGraph {

    Set<Vector> initOffsets(GeometryProperty property);

    GeometryView getView();
}
