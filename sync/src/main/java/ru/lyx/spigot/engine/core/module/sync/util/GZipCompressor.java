package ru.lyx.spigot.engine.core.module.sync.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class GZipCompressor {

    public byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        deflater.finish();
        byte[] buffer = new byte[data.length];

        while (!deflater.finished()) {

            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        outputStream.close();
        return outputStream.toByteArray();
    }

    public byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[data.length];

        while (!inflater.finished()) {

            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        outputStream.close();
        return outputStream.toByteArray();
    }
}
