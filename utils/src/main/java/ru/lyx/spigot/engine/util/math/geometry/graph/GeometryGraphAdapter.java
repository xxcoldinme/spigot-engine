package ru.lyx.spigot.engine.util.math.geometry.graph;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import ru.lyx.spigot.engine.util.math.geometry.GeometryProperty;
import ru.lyx.spigot.engine.util.math.geometry.GeometryView;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public abstract class GeometryGraphAdapter implements GeometryGraph {

    private final Location middle;
    private final GeometryProperty property;

    private Set<Vector> offsets;

    private void initOffsets() {
        if (offsets == null) {
            offsets = initOffsets(property);
        }
    }

    @Override
    public final GeometryView getView() {
        initOffsets();
        return new GeometryView(middle, offsets);
    }

    protected Set<Vector> newOffsetsSet() {
        return new HashSet<>();
    }
}
