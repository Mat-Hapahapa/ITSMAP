package com.mat.mik.handin2and3.Models;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mikkel on 05-05-2016.
 */
public class WeatherInfo {
    public long id;
    public String description;
    public double temperature;
    public Date timestamp;

    public WeatherInfo() {}
    public WeatherInfo(String description, double temperature) {
        this.description = description;
        this.temperature = temperature;
        timestamp = Calendar.getInstance().getTime();
    }
}
