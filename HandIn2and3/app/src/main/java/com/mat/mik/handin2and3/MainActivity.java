package com.mat.mik.handin2and3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.mat.mik.handin2and3.Adaptors.WeatherInfoListAdaptor;
import com.mat.mik.handin2and3.Models.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView weatherList;
    private WeatherInfoListAdaptor wAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Whole assignment
        // TODO: Populate ListView

        final ArrayList<WeatherInfo> someWeatherListtoDelete = new ArrayList<WeatherInfo>();
        for(int i = 0; i < 7; i++){
            someWeatherListtoDelete.add(new WeatherInfo("f", 123));
        }

        wAdaptor = new WeatherInfoListAdaptor(this, someWeatherListtoDelete);
        weatherList = (ListView) findViewById(R.id.historyList);
        weatherList.setAdapter(wAdaptor);


        /* Change icon to match current weather - from project: https://github.com/pwittchen/WeatherIconView */
        WeatherIconView weatherIconView;
        weatherIconView = (WeatherIconView) findViewById(R.id.weatherImg);
        //weatherIconView.setIconResource(getString(R.string.wi_day_sunny_overcast));
        weatherIconView.setIconResource(getString(R.string.wi_day_cloudy));
        weatherIconView.setIconSize(100);
        weatherIconView.setIconColor(Color.BLACK);

    }
}
