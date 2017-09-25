package com.alexfu.phoenix;

public class VersionHistory {
    public final int lastVersionCode;
    public final int currentVersionCode;

    public VersionHistory(int lastVersionCode, int currentVersionCode) {
        this.lastVersionCode = lastVersionCode;
        this.currentVersionCode = currentVersionCode;
    }
}
