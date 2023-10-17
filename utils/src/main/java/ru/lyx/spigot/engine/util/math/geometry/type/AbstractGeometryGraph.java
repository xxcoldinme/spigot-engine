package ru.lyx.spigot.engine.util.math.geometry.type;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import ru.lyx.spigot.engine.util.math.VectorUtils;
import ru.lyx.spigot.engine.util.math.geometry.GeometryGraph;
import ru.lyx.spigot.engine.util.math.geometry.GeometryGraphProperty;

import java.util.Set;
import java.util.function.Consumer;

@Getter(onMethod_ = {@NonNull})
@ToString
@RequiredArgsConstructor
public abstract class AbstractGeometryGraph implements GeometryGraph {

    private final GeometryGraphProperty property;
    private Set<Vector> offsets;

    protected abstract Set<Vector> createOffsets(GeometryGraphProperty property);

    public final void init() {
        offsets = createOffsets(property);
    }

    @Override
    public final void apply(Location center, Consumer<Location> pointHandler) {
        offsets.forEach(vectorOffset -> pointHandler.accept(
                VectorUtils.applyOffset(center, vectorOffset)));
    }
}
