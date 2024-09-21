package com.example.crop_management_system;

public class SoilHumidityEntry {
    private long timestamp;
    private int value;

    public SoilHumidityEntry() {
    }

    public SoilHumidityEntry(long timestamp, int value) {
        this.setTimestamp(timestamp);
        this.setValue(value);
    }

    public String toString() {
        return getTimestamp() + ": " + getValue();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
