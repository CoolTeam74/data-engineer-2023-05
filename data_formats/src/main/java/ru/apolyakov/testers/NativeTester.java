package ru.apolyakov.testers;

import ru.apolyakov.Stat;
import ru.apolyakov.Structure;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class NativeTester implements AbstractTester {
    public Stat test(Structure structure) throws IOException {
        long start=0, stop=0;
        Path file = null;
        try {
            file = Files.createTempFile("native-serialization", ".bin");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(file))) {
            start = System.currentTimeMillis();
            objectOutputStream.writeObject(structure);
            stop = System.currentTimeMillis();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long serTime = stop - start;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(file))) {
            start = System.currentTimeMillis();
            objectInputStream.readObject();
            stop = System.currentTimeMillis();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //file.toFile().deleteOnExit();
        return new Stat(serTime, stop - start, Files.size(file));
    }
}
