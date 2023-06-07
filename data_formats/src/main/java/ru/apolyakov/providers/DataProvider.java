package ru.apolyakov.providers;

import ru.apolyakov.Structure;

import java.util.HashMap;
import java.util.Map;

public class DataProvider {
    public static Structure getFilledData()
    {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        return new Structure("word", new String[]{"word1", "word2", "word3"}, map, 56L, 44.21);
    }
}
