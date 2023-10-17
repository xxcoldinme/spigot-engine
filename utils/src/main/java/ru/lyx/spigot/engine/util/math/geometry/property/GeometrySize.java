package ru.lyx.spigot.engine.util.math.geometry.property;

import lombok.ToString;
import lombok.Value;

@ToString
@Value(staticConstructor = "of")
public class GeometrySize {

    public static GeometrySize of(int size) {
        return of(size, size, size);
    }

    double x;
    double y;
    double z;
}
