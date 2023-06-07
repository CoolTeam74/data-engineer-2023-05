package ru.apolyakov;

import org.msgpack.annotation.Message;

import java.io.Serializable;
import java.util.Map;

@Message
public class Structure implements Serializable {
    private String stringValue;
    private String[] array;
    private Map<String, String> map;
    private long longValue;
    private double doubleValue;

    public Structure() {
    }

    public Structure(String stringValue, String[] array, Map<String, String> map, Long longValue, Double doubleValue) {
        this.stringValue = stringValue;
        this.array = array;
        this.map = map;
        this.longValue = longValue;
        this.doubleValue = doubleValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }


}


