package ru.lyx.spigot.engine.core.module.processor.transaction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.metadata.MetadataProperty;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessTransaction {

    public static ProcessTransaction create(AttachmentContainer<SpigotModuleProcessor<?, ?>> attachmentContainer) {
        return new ProcessTransaction(attachmentContainer, SpigotMetadata.create());
    }

    private final AttachmentContainer<SpigotModuleProcessor<?, ?>> attachmentContainer;
    private final SpigotMetadata metadata;

    private LinkedProcessor next;

    public final void doNext() {
        next = null;
    }

    public ProcessTransaction thenContinueTo(Class<? extends SpigotModuleProcessor<?, ?>> processor) {
        next = new LinkedProcessor(processor);
        return this;
    }

    public ProcessTransaction withMetadata(KeyProperty<?> key, Object value) {
        metadata.with(MetadataProperty.of(key, value));
        return this;
    }
}
