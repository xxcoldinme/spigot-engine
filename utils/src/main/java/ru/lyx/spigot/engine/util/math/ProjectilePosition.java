package ru.lyx.spigot.engine.util.math;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;

@Getter
@ToString
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ProjectilePosition {

    HEAD(1.5D, 2.0D),
    BODY(0.6D, 1.5D),
    LEGS(0.0D, 0.6D),

    UNKNOWN(-1, -1),
    ;

    double minRange;
    double maxRange;

    public static ProjectilePosition fromHitProjectile(@NonNull Entity target, @NonNull Projectile hitProjectile) {
        return fromHitLocation(target, hitProjectile.getLocation());
    }

    public static ProjectilePosition fromHitLocation(@NonNull Entity target, @NonNull Location hitLocation) {
        return fromHitLocation(target.getLocation(), hitLocation);
    }

    public static ProjectilePosition fromHitLocation(@NonNull Location entityLocation, @NonNull Location hitLocation) {
        double distanceY = hitLocation.getY() - entityLocation.getY();

        for (ProjectilePosition hitbox : ProjectilePosition.values()) {

            if (distanceY >= hitbox.minRange && distanceY <= hitbox.maxRange) {
                return hitbox;
            }
        }

        return UNKNOWN;
    }
}
