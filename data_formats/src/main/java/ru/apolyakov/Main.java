package ru.apolyakov;

import ru.apolyakov.providers.DataProvider;
import ru.apolyakov.providers.TesterProvider;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Structure structure = DataProvider.getFilledData();
        Stat nativeStat = TesterProvider.getNativeTester().test(structure);
        System.out.println("native-serialization: size: " + nativeStat.getSize() + "; serTime: " + nativeStat.getSerTime() + "; deserTime: " + nativeStat.getDeserTime());

        Stat xmlStat = TesterProvider.getXmlTester().test(structure);
        System.out.println("xml-serialization: size: " + xmlStat.getSize() + "; serTime: " + xmlStat.getSerTime() + "; deserTime: " + xmlStat.getDeserTime());

        Stat jsonStat = TesterProvider.getJsonTester().test(structure);
        System.out.println("json-serialization: size: " + jsonStat.getSize() + "; serTime: " + jsonStat.getSerTime() + "; deserTime: " + jsonStat.getDeserTime());

        Stat msPackStat = TesterProvider.getMSpackTester().test(structure);
        System.out.println("MSpack-serialization: size: " + msPackStat.getSize() + "; serTime: " + msPackStat.getSerTime() + "; deserTime: " + msPackStat.getDeserTime());

        Stat yamlStat = TesterProvider.getYamlTester().test(structure);
        System.out.println("YAML-serialization: size: " + yamlStat.getSize() + "; serTime: " + yamlStat.getSerTime() + "; deserTime: " + yamlStat.getDeserTime());

        Stat gpbStat = TesterProvider.getGPBTester().test(structure);
        System.out.println("GPB-serialization: size: " + gpbStat.getSize() + "; serTime: " + gpbStat.getSerTime() + "; deserTime: " + gpbStat.getDeserTime());
    }
}
