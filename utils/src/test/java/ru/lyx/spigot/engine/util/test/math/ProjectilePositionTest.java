package ru.lyx.spigot.engine.util.test.math;

import org.bukkit.Location;
import ru.lyx.spigot.engine.util.math.ProjectilePosition;

public class ProjectilePositionTest {

    public static void main(String[] args) {
        Location entityLoc = new Location(null, 567.25423, 78.3, -56.13846);
        Location hitLoc = entityLoc.clone().add(0, 0.5, 0);

        ProjectilePosition projectilePosition
                = ProjectilePosition.fromHitLocation(entityLoc, hitLoc);

        System.out.println(projectilePosition);
    }
}
