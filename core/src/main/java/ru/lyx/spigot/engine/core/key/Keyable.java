package ru.lyx.spigot.engine.core.key;

/**
 * Implementation of this interface means that the object is bound to a certain key,
 * which will be used to cache or retrieve this object in the future.
 *
 * @param <T> Type of specific key value.
 */
public interface Keyable<T> {

    KeyProperty<T> getKey();
}
