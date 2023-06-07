package ru.apolyakov.testers;

import com.google.gson.Gson;
import ru.apolyakov.Stat;
import ru.apolyakov.Structure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonTester implements AbstractTester {
    @Override
    public Stat test(Structure structure) throws IOException {
        long start, stop;
        Gson gson = new Gson();
        Path file = Files.createTempFile("json-serialization", ".json");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(file)) {
            start = System.currentTimeMillis();
            gson.toJson(structure, bufferedWriter);
            stop = System.currentTimeMillis();
        }
        long serTime = stop - start;
        try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {
            start = System.currentTimeMillis();
            gson.fromJson(bufferedReader, Structure.class);
            stop = System.currentTimeMillis();
        }
        //file.toFile().deleteOnExit();
        return new Stat(serTime, stop - start, Files.size(file));
    }
}
