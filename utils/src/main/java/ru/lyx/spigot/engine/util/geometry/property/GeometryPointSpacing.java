package ru.lyx.spigot.engine.util.geometry.property;

import lombok.ToString;
import lombok.Value;

@ToString
@Value(staticConstructor = "of")
public class GeometryPointSpacing {

    double space;
}
