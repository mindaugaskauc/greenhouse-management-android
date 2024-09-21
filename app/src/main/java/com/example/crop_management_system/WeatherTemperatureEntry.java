package com.example.crop_management_system;

public class WeatherTemperatureEntry {
    private long timestamp;
    private float value;

    public WeatherTemperatureEntry() {
    }

    public WeatherTemperatureEntry(long timestamp, float value) {
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
