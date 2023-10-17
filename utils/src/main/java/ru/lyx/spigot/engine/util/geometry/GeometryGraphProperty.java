package ru.lyx.spigot.engine.util.geometry;

import lombok.*;
import ru.lyx.spigot.engine.util.geometry.property.GeometryLength;
import ru.lyx.spigot.engine.util.geometry.property.GeometryPointSpacing;
import ru.lyx.spigot.engine.util.geometry.property.GeometryRotation;
import ru.lyx.spigot.engine.util.geometry.property.GeometrySize;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "newBuilder", setterPrefix = "set")
public class GeometryGraphProperty {

    private static final GeometryPointSpacing DEF_POINT_SPACING = GeometryPointSpacing.of(0.1);
    private static final GeometryRotation DEF_ROTATION = GeometryRotation.of(0, 0);
    private static final GeometrySize DEF_SIZE = GeometrySize.of(5);
    private static final GeometryLength DEF_LENGTH = GeometryLength.of(15);

    public static GeometryGraphProperty create() {
        return new GeometryGraphProperty()
                .setDefaults();
    }

    public static GeometryGraphProperty create(GeometryPointSpacing pointSpacing, GeometryRotation rotation,
                                               GeometrySize size, GeometryLength length) {
        return new GeometryGraphProperty(pointSpacing, rotation, size, length);
    }

    private GeometryPointSpacing pointSpacing;
    private GeometryRotation rotation;
    private GeometrySize size;
    private GeometryLength length;

    public GeometryGraphProperty(GeometryPointSpacing pointSpacing, GeometryRotation rotation,
                                 GeometrySize size, GeometryLength length) {
        setDefaults();

        this.pointSpacing = (pointSpacing == null ? DEF_POINT_SPACING : pointSpacing);
        this.rotation = (rotation == null ? DEF_ROTATION : rotation);
        this.size = (size == null ? DEF_SIZE : size);
        this.length = (length == null ? DEF_LENGTH : length);
    }

    public GeometryGraphProperty setDefaults() {
        pointSpacing = DEF_POINT_SPACING;
        rotation = DEF_ROTATION;
        size = DEF_SIZE;
        length = DEF_LENGTH;
        return this;
    }
}
