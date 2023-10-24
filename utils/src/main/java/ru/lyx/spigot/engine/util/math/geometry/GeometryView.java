package ru.lyx.spigot.engine.util.math.geometry;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class GeometryView {
    
    private final Location center;
    private final Set<Vector> offsets;
    
    public Stream<Location> stream() {
        return offsets.stream().map(center.clone()::add);
    }

    public <T> void spawnParticles(Particle particle, int particlesCount, T data) {
        stream().forEach(location -> location.getWorld().spawnParticle(particle, location, particlesCount, 0, 0, 0, 0, data));
    }

    public <T> void spawnParticles(Particle particle, T data) {
        spawnParticles(particle, 1, data);
    }

    public void spawnParticles(Particle particle, int particlesCount) {
        spawnParticles(particle, particlesCount, null);
    }

    public void spawnParticles(Particle particle) {
        spawnParticles(particle, 1, null);
    }

    public <T> void spawnParticles(Function<Location, Particle> particleFactory, int particlesCount, T data) {
        stream().forEach(location -> location.getWorld().spawnParticle(particleFactory.apply(location), location, particlesCount, 0, 0, 0, 0, data));
    }

    public <T> void spawnParticles(Function<Location, Particle> particleFactory, T data) {
        spawnParticles(particleFactory, 1, data);
    }

    public void spawnParticles(Function<Location, Particle> particleFactory, int particlesCount) {
        spawnParticles(particleFactory, particlesCount, null);
    }

    public void spawnParticles(Function<Location, Particle> particleFactory) {
        spawnParticles(particleFactory, 1, null);
    }

    public void dropItemsNaturally(Location center, ItemStack itemStack) {
        stream().forEach(location -> location.getWorld().dropItemNaturally(location, itemStack));
    }

    public void dropItemsNaturallyRandom(Location center, List<ItemStack> itemStacks) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        stream().forEach(location -> location.getWorld().dropItemNaturally(location,
                itemStacks.get(random.nextInt(1, itemStacks.size()) - 1)));
    }

    public void dropItemsNaturallyRandom(Location center, Function<Location, ItemStack> itemFactory) {
        stream().forEach(location -> location.getWorld().dropItemNaturally(location, itemFactory.apply(location)));
    }

    public void spawnEntities(Location center, EntityType entityType) {
        stream().forEach(location -> location.getWorld().spawnEntity(location, entityType));
    }
}
