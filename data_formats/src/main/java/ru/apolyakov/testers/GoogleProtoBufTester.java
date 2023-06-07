package ru.apolyakov.testers;

import ru.apolyakov.Stat;
import ru.apolyakov.Structure;
import ru.apolyakov.generated.StructureProto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class GoogleProtoBufTester implements AbstractTester {
    @Override
    public Stat test(Structure structure) throws IOException {
        long start, stop;
        StructureProto.MapFieldEntry entry1 = StructureProto.MapFieldEntry.newBuilder()
                .setKey("key1").setValue("value1").build();
        StructureProto.MapFieldEntry entry2 = StructureProto.MapFieldEntry.newBuilder()
                .setKey("key2").setValue("value2").build();
        StructureProto.MapFieldEntry entry3 = StructureProto.MapFieldEntry.newBuilder()
                .setKey("key3").setValue("value3").build();

        StructureProto.Structure_Message message = StructureProto.Structure_Message.newBuilder()
                .setStringValue("word")
                .addArray("word1")
                .addArray("word2")
                .addArray("word3")
                .addMapField(entry1)
                .addMapField(entry2)
                .addMapField(entry3)
                .setDoubleValue(44.21)
                .setLongValue(56L)
                .build();

        Path file = Files.createTempFile("google-protocol-buffers-serialization", ".ser");
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(file)) {
            // write
            start = System.currentTimeMillis();
            FileOutputStream output = new FileOutputStream(file.toAbsolutePath().toString());
            message.writeTo(output);
            output.close();
            stop = System.currentTimeMillis();
        }

        long serTime = stop - start;

        try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {
            // read
            start = System.currentTimeMillis();
            StructureProto.Structure_Message structureMessage = StructureProto.Structure_Message.parseFrom(new FileInputStream(file.toAbsolutePath().toString()));
            //System.out.println(structureMessage);
            stop = System.currentTimeMillis();
        }

        //file.toFile().deleteOnExit();
        return new Stat(serTime, stop - start, Files.size(file));
    }
}
