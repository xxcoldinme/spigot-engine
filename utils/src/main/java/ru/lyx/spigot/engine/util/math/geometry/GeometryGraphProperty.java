package ru.lyx.spigot.engine.util.math.geometry;

import lombok.ToString;
import lombok.Value;
import ru.lyx.spigot.engine.util.math.geometry.property.GeometryPointSpacing;
import ru.lyx.spigot.engine.util.math.geometry.property.GeometryRotation;
import ru.lyx.spigot.engine.util.math.geometry.property.GeometrySize;

@ToString
@Value(staticConstructor = "create")
public class GeometryGraphProperty {

    GeometryPointSpacing pointSpacing;
    GeometryRotation rotation;
    GeometrySize size;
}
