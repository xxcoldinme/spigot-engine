package ru.lyx.spigot.engine.core.module.handler;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.module.SpigotModule;

/**
 * This interface is a handler for incoming events
 * from SpigotEngine modules.
 * <p>
 * In order to correctly implement this interface, it is worth knowing
 * just a few things that will help to understand more about
 * the device of incoming events:
 * <p>
 * 1. {@code validate()} method is responsible for the condition under
 *    which your implementation of the handler will pay attention to the incoming event.
 *    If the event does not fit the needs of the handler, you will need to return {@code false} for it;
 * <p>
 * 2. {@code ALWAYS} and {@code NEVER} constants are auxiliary for the implementation
 *    of {@code validate()} method - If you need to handle all incoming module events,
 *    then use {@code ALWAYS}, and if you do not need to take the handler into account at all,
 *    then there is {@code NEVER};
 * <p>
 * 3. If the validation is correct and produces a {@code true} value,
 *    then the algorithm reproduces the {@code handle()} method, which implies the handling
 *    of an incoming event from the module specified in the generic as type.
 */
public interface SpigotHandler<T extends SpigotModule<?, ?>> {

// -------------------------- // VALIDATION // ----------------------------------------- //

    Boolean ALWAYS = Boolean.TRUE;
    Boolean NEVER = Boolean.FALSE;

// ------------------------------------------------------------------------------------- //

    /**
     * Validate the incoming module event.
     *
     * @param context Incoming event context.
     */
    boolean validate(@NotNull SpigotHandlerContext<T> context);

    /**
     * Handler of the incoming module event.
     *
     * @param context Incoming event context.
     */
    void handle(@NotNull SpigotHandlerContext<T> context);
}
