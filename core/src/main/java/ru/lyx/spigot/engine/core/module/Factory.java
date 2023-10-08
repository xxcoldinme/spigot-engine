package ru.lyx.spigot.engine.core.module;

import java.lang.annotation.*;

/**
 * This annotation is needed to get the factory of modules,
 * by which they are further created and registered inside SpigotEngine.
 * <p>
 * To get the factory on which this annotation stands,
 * an internal helper is used - {@code SpigotModuleFactoryHelper.of()}
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Factory {
}
