package com.example.crop_management_system;

public class SoilTemperatureEntry {
    private long timestamp;
    private float value;

    public SoilTemperatureEntry() {
    }

    public SoilTemperatureEntry(long timestamp, float value) {
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

    public void setValue(float value) {
        this.value = value;
    }
}
