package ru.apolyakov.testers;

import org.msgpack.MessagePack;
import ru.apolyakov.Stat;
import ru.apolyakov.Structure;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class MSpackTester implements AbstractTester {
    @Override
    public Stat test(Structure structure) throws IOException {
        long start, stop;
        MessagePack messagePack = new MessagePack();
        Path file = Files.createTempFile("MSpack-serialization", ".txt");
        try (OutputStream outputStream = Files.newOutputStream(file)) {
            start = System.currentTimeMillis();
            messagePack.write(outputStream, structure);
            stop = System.currentTimeMillis();
        }
        long serTime = stop - start;
        try (InputStream inputStream = Files.newInputStream(file)) {
            start = System.currentTimeMillis();
            messagePack.read(inputStream, Structure.class);
            stop = System.currentTimeMillis();
        }
        //file.toFile().deleteOnExit();
        return new Stat(serTime, stop - start, Files.size(file));
    }
}
