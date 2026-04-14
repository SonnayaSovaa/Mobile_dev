package ru.mirea.shutovks.mireaproject.network;

import java.util.List;

public class HourlyWeatherResponse {
    public double latitude;
    public double longitude;
    public String timezone;
    public Hourly hourly;

    public static class Hourly {
        public List<String> time;
        public List<Double> temperature_2m;
    }
}