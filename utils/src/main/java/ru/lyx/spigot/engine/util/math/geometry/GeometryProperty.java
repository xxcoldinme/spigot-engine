package ru.lyx.spigot.engine.util.math.geometry;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder(toBuilder = true)
public class GeometryProperty {

    private double sizeX, sizeY, sizeZ;
    private float rotationX, rotationY;
    private double pointsSpace;
}
