package ru.apolyakov.testers;

import ru.apolyakov.Stat;
import ru.apolyakov.Structure;

import java.io.IOException;

public interface AbstractTester {
    Stat test(Structure structure) throws IOException;
}
