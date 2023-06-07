package ru.apolyakov;

public class Stat {
    private final long serTime;
    private final long deserTime;
    private final long size;

    public Stat(long serTime, long deserTime, long size) {
        this.serTime = serTime;
        this.deserTime = deserTime;
        this.size = size;
    }

    public long getSerTime() {
        return serTime;
    }

    public long getDeserTime() {
        return deserTime;
    }

    public long getSize() {
        return size;
    }
}
