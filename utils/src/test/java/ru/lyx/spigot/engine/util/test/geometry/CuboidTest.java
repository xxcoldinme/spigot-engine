package ru.lyx.spigot.engine.util.test.geometry;

import org.bukkit.Location;
import ru.lyx.spigot.engine.util.math.geometry.Geometry;
import ru.lyx.spigot.engine.util.math.geometry.GeometryProperty;
import ru.lyx.spigot.engine.util.math.geometry.GeometryView;
import ru.lyx.spigot.engine.util.math.geometry.graph.GeometryGraph;

public class CuboidTest {

    public static void main(String[] args) {
        GeometryProperty property = GeometryProperty.builder()
                .rotationX(1f)
                .rotationY(2.56f)
                .pointsSpace(0.3)
                .sizeX(3).sizeY(3).sizeZ(3)
                .build();

        System.out.println(property);

        Location location = new Location(null, 45, 3, -901);
        GeometryGraph cuboid = Geometry.of(location, property).createCube();

        GeometryView view = cuboid.getView();

        view.handleView(location1 -> System.out.println(location1.toVector()));
    }
}
