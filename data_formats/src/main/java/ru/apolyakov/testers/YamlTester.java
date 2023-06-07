package ru.apolyakov.testers;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import ru.apolyakov.Stat;
import ru.apolyakov.Structure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class YamlTester implements AbstractTester {
    @Override
    public Stat test(Structure structure) throws IOException {
        long start, stop;
        Path file = Files.createTempFile("YAML-serialization", ".yaml");
        YamlWriter yamlWriter = null;
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(file)) {
            yamlWriter = new YamlWriter(bufferedWriter);
            start = System.currentTimeMillis();
            yamlWriter.write(structure);
            yamlWriter.close();
            stop = System.currentTimeMillis();
        }
        long serTime = stop - start;
        try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {
            YamlReader yamlReader = new YamlReader(bufferedReader);
            start = System.currentTimeMillis();
            yamlReader.read();
            yamlReader.close();
            stop = System.currentTimeMillis();
        }
        //file.toFile().deleteOnExit();
        return new Stat(serTime, stop - start, Files.size(file));
    }
}
