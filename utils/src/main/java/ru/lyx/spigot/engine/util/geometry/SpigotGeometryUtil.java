package ru.lyx.spigot.engine.util.geometry;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

@UtilityClass
public class SpigotGeometryUtil {

    public <T> void spawnParticles(GeometryGraph graph, Particle particle, int particlesCount, Location center, T data) {
        graph.apply(center, location -> location.getWorld().spawnParticle(particle, location, particlesCount, 0, 0, 0, 0, data));
    }

    public <T> void spawnParticles(GeometryGraph graph, Particle particle, Location center, T data) {
        spawnParticles(graph, particle, 1, center, data);
    }

    public void spawnParticles(GeometryGraph graph, Particle particle, int particlesCount, Location center) {
        spawnParticles(graph, particle, particlesCount, center, null);
    }

    public void spawnParticles(GeometryGraph graph, Particle particle, Location center) {
        spawnParticles(graph, particle, 1, center, null);
    }

    public <T> void spawnParticles(GeometryGraph graph, Function<Location, Particle> particleFactory, int particlesCount, Location center, T data) {
        graph.apply(center, location -> location.getWorld().spawnParticle(particleFactory.apply(location), location, particlesCount, 0, 0, 0, 0, data));
    }

    public <T> void spawnParticles(GeometryGraph graph, Function<Location, Particle> particleFactory, Location center, T data) {
        spawnParticles(graph, particleFactory, 1, center, data);
    }

    public void spawnParticles(GeometryGraph graph, Function<Location, Particle> particleFactory, int particlesCount, Location center) {
        spawnParticles(graph, particleFactory, particlesCount, center, null);
    }

    public void spawnParticles(GeometryGraph graph, Function<Location, Particle> particleFactory, Location center) {
        spawnParticles(graph, particleFactory, 1, center, null);
    }

    public void dropItemsNaturally(GeometryGraph graph, Location center, ItemStack itemStack) {
        graph.apply(center, location -> location.getWorld().dropItemNaturally(location, itemStack));
    }

    public void dropItemsNaturallyRandom(GeometryGraph graph, Location center, List<ItemStack> itemStacks) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        graph.apply(center, location -> location.getWorld().dropItemNaturally(location,
                itemStacks.get(random.nextInt(1, itemStacks.size()) - 1)));
    }

    public void dropItemsNaturallyRandom(GeometryGraph graph, Location center, Function<Location, ItemStack> itemFactory) {
        graph.apply(center, location -> location.getWorld().dropItemNaturally(location, itemFactory.apply(location)));
    }

    public void spawnEntities(GeometryGraph graph, Location center, EntityType entityType) {
        graph.apply(center, location -> location.getWorld().spawnEntity(location, entityType));
    }
}
