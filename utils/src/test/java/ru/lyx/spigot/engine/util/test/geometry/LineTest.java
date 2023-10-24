package ru.lyx.spigot.engine.util.test.geometry;

import org.bukkit.Location;
import ru.lyx.spigot.engine.util.math.geometry.Geometry;
import ru.lyx.spigot.engine.util.math.geometry.GeometryProperty;
import ru.lyx.spigot.engine.util.math.geometry.GeometryView;
import ru.lyx.spigot.engine.util.math.geometry.graph.GeometryGraph;

public class LineTest {

    public static void main(String[] args) {
        GeometryProperty property = GeometryProperty.builder()
                .rotationX(1f)
                .rotationY(2.56f)
                .pointsSpace(0.1)
                .sizeX(25)
                .build();

        System.out.println(property);

        Location location = new Location(null, 45, 3, -901);
        GeometryGraph cuboid = Geometry.of(location, property).createLine();

        GeometryView view = cuboid.getView();

        view.handleView(location1 -> System.out.println(location1.toVector()));
    }
}
