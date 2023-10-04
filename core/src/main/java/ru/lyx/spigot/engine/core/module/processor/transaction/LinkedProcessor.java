package ru.lyx.spigot.engine.core.module.processor.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;

@Getter
@ToString
@RequiredArgsConstructor
public class LinkedProcessor {

    private final AttachmentContainer<SpigotModuleProcessor<?, ?>> attachmentContainer;
    private final Class<? extends SpigotModuleProcessor<?, ?>> processorClass;

    public SpigotModuleProcessor<?, ?> newProcessorInstance() {
        return null; // TODO
    }
}
