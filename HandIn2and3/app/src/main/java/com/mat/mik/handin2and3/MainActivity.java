package com.mat.mik.handin2and3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.pwittchen.weathericonview.WeatherIconView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Whole assignment
        // TODO: Populate ListView

        /* Change icon to match current weather - from project: https://github.com/pwittchen/WeatherIconView */
        WeatherIconView weatherIconView;
        weatherIconView = (WeatherIconView) findViewById(R.id.weatherImg);
        //weatherIconView.setIconResource(getString(R.string.wi_day_sunny_overcast));
        weatherIconView.setIconResource(getString(R.string.wi_day_cloudy));
        weatherIconView.setIconSize(100);
        weatherIconView.setIconColor(Color.BLACK);

    }
}
