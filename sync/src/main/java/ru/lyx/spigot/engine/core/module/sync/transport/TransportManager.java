package ru.lyx.spigot.engine.core.module.sync.transport;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.module.sync.SpigotSyncModuleException;
import ru.lyx.spigot.engine.core.module.sync.util.GZipCompressor;

import java.io.IOException;
import java.util.zip.DataFormatException;

@RequiredArgsConstructor
public class TransportManager {

    private final GZipCompressor compressor;

    public byte[] toData(@NotNull TransportObject transportObject) {
        try {
            byte[] bytes = SerializationUtils.serialize(transportObject);
            return compressor.compress(bytes);
        }
        catch (IOException exception) {
            throw new SpigotSyncModuleException(exception);
        }
    }

    public TransportObject fromData(byte[] array) {
        try {
            byte[] decompressedBytes = compressor.decompress(array);
            return SerializationUtils.deserialize(decompressedBytes);
        }
        catch (IOException | DataFormatException e) {
            throw new SpigotSyncModuleException(e);
        }
    }
}
