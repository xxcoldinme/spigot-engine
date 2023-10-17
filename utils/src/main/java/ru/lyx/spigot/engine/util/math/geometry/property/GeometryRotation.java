package ru.lyx.spigot.engine.util.math.geometry.property;

import lombok.ToString;
import lombok.Value;

@ToString
@Value(staticConstructor = "of")
public class GeometryRotation {

    float x;
    float y;
}
