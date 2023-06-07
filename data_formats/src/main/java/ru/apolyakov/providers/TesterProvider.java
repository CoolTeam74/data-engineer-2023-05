package ru.apolyakov.providers;

import ru.apolyakov.testers.*;

public class TesterProvider {
    private static final AbstractTester[] TESTERS = {new NativeTester(), new XmlTester(), new JsonTester(), new MSpackTester(), new YamlTester(), new GoogleProtoBufTester()};

    public static AbstractTester getNativeTester()
    {
        return TESTERS[0];
    }

    public static AbstractTester getXmlTester()
    {
        return TESTERS[1];
    }

    public static AbstractTester getJsonTester()
    {
        return TESTERS[2];
    }

    public static AbstractTester getMSpackTester()
    {
        return TESTERS[3];
    }

    public static AbstractTester getYamlTester()
    {
        return TESTERS[4];
    }

    public static AbstractTester getGPBTester()
    {
        return TESTERS[5];
    }
}
