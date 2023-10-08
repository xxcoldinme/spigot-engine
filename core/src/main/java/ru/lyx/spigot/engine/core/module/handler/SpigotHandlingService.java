package ru.lyx.spigot.engine.core.module.handler;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import ru.lyx.spigot.engine.core.SpigotContainer;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.attachment.plugin.PluginProperty;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.module.SpigotModule;
import ru.lyx.spigot.engine.core.reflection.GetGenericTypeHandler;
import ru.lyx.spigot.engine.core.reflection.ReflectionService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SpigotHandlingService {

    private final SpigotEngine engine;
    private final SpigotContainer container;
    private final ReflectionService reflectionService;

    private AttachmentContainer<SpigotHandler<?>> getTotalHandlers() {
        final AttachmentContainer<PluginProperty<SpigotHandler<?>>> attachmentContainer
                = container.getHandlers().getParent();
        return AttachmentContainer.of(
                attachmentContainer.getDefinitions()
                        .stream()
                        .map(PluginProperty::getDefinition)
                        .collect(Collectors.toList()));
    }

    private AttachmentContainer<SpigotHandler<?>> getPluginHandlers(Plugin plugin) {
        final Collection<PluginProperty<SpigotHandler<?>>> collection = container.getHandlers()
                .findByPlugin(plugin);
        return AttachmentContainer.of(
                collection.stream()
                        .map(PluginProperty::getDefinition)
                        .collect(Collectors.toList()));
    }

    private AttachmentContainer<SpigotHandler<?>> getModuleHandlers(Class<? extends SpigotModule<?, ?>> cls) {
        final List<SpigotHandler<?>> definitions = getTotalHandlers()
                .getDefinitions();
        return AttachmentContainer.of(
                definitions.stream()
                        .filter(handler -> cls.equals(lookupParentModule(handler.getClass())))
                        .toArray(SpigotHandler<?>[]::new));
    }

    @SuppressWarnings("unchecked")
    private <T extends SpigotModule<?, ?>> void sendHandlerGeneric(SpigotHandler<T> handler, SpigotHandlingTrigger trigger, SpigotMetadata metadata) {
        final Class<T> parentClass = (Class<T>) lookupParentModule(handler.getClass());
        final T spigotModule = engine.lookupModule(parentClass);

        final SpigotHandlerContext<T> context = new SpigotHandlerContext<>(trigger, engine, metadata, spigotModule);

        if (handler.validate(context)) {
            handler.handle(context);
        }
    }

    private void sendHandler(AttachmentContainer<SpigotHandler<?>> handlers, SpigotHandlingTrigger trigger, SpigotMetadata metadata) {
        handlers.getDefinitions()
                .forEach(spigotModuleHandler -> sendHandlerGeneric(spigotModuleHandler, trigger, metadata));
    }

    public void sendTotalHandler(SpigotHandlingTrigger trigger, SpigotMetadata metadata) {
        sendHandler(getTotalHandlers(), trigger, metadata);
    }

    public void sendPluginHandler(Plugin plugin, SpigotHandlingTrigger trigger, SpigotMetadata metadata) {
        sendHandler(getPluginHandlers(plugin), trigger, metadata);
    }

    public void sendModuleHandler(Class<? extends SpigotModule<?, ?>> cls, SpigotHandlingTrigger trigger, SpigotMetadata metadata) {
        sendHandler(getModuleHandlers(cls), trigger, metadata);
    }

    @SuppressWarnings("rawtypes")
    private Class<? extends SpigotModule<?, ?>> lookupParentModule(Class<? extends SpigotHandler> handler) {
        return reflectionService.getGenericType(
                SpigotMetadata.create()
                        .with(GetGenericTypeHandler.GENERIC_TYPE_INDEX, 0)
                        .with(GetGenericTypeHandler.TARGET_CLASS, handler));
    }

}
