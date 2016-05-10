package com.mat.mik.handin2and3.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mikkel on 05-05-2016.
 */
public class WeatherInfo {
    private static final String TAG = WeatherInfo.class.getSimpleName();
    private static final String JSON_KEY_MAIN = "main";
    private static final String JSON_KEY_WEATHER = "weather";
    private static final String JSON_KEY_DESCRIPTION = "description";
    private static final String JSON_KEY_TEMP = "temp";

    private static final double kelvinCelsiusDiff = -272.15;

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

    public WeatherInfo(String json) {

        String description = "N/A";
        double temp = 404;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject mainObject = jsonObject.getJSONObject(JSON_KEY_MAIN);
            JSONArray weatherArray = jsonObject.getJSONArray(JSON_KEY_WEATHER);
            JSONObject weatherObject = weatherArray.getJSONObject(0); //when only getting info from one city, the current weahter is always in the first spot

            description = weatherObject.getString(JSON_KEY_DESCRIPTION);
            temp = mainObject.getDouble(JSON_KEY_TEMP);
            temp += kelvinCelsiusDiff;
        } catch (JSONException ex) {
            Log.e(TAG, ex.toString());
        }

        this.description = description;
        this.temperature = temp;
        timestamp = Calendar.getInstance().getTime();
    }
}
