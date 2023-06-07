package ru.apolyakov.testers;

import ru.apolyakov.Stat;
import ru.apolyakov.Structure;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class XmlTester implements AbstractTester {
    @Override
    public Stat test(Structure structure) throws IOException {
        long start, stop;
        Path file = Files.createTempFile("xml-serialization", ".xml");
        try (XMLEncoder xmlEncoder = new XMLEncoder(Files.newOutputStream(file))) {
            start = System.currentTimeMillis();
            xmlEncoder.writeObject(structure);
            stop = System.currentTimeMillis();
        }
        long serTime = stop - start;
        try (XMLDecoder xmlDecoder = new XMLDecoder(Files.newInputStream(file))) {
            start = System.currentTimeMillis();
            xmlDecoder.readObject();
            stop = System.currentTimeMillis();
        }
        //file.toFile().deleteOnExit();
        return new Stat(serTime, stop - start, Files.size(file));
    }
}
