package com.mat.mik.handin2and3;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.mat.mik.handin2and3.Adaptors.WeatherInfoListAdaptor;
import com.mat.mik.handin2and3.Models.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView weatherList;
    private WeatherInfoListAdaptor wAdaptor;
    BackgroundDataService mService;
    private WeatherInfo currWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent serviceIntet = new Intent(this, BackgroundDataService.class);
        bindService(serviceIntet, mServiceConnection, Context.BIND_AUTO_CREATE);

    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BackgroundDataService.LocalBinder binder = (BackgroundDataService.LocalBinder) service;
            mService = binder.getService();
            setCurrentWeather();

            setPastWeather();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setCurrentWeather() {
        currWeather = mService.getCurrentWeather();

        /* Change icon to match current weather - from project: https://github.com/pwittchen/WeatherIconView */
        WeatherIconView weatherIconView;
        weatherIconView = (WeatherIconView) findViewById(R.id.weatherImg);
        weatherIconView.setIconResource(getString(R.string.wi_day_cloudy));

        if (currWeather.description != null) {
            TextView CurrDesc = (TextView) findViewById(R.id.weatherInfoText);
            CurrDesc.setText(currWeather.description);
        }
        TextView CurrTemp = (TextView) findViewById(R.id.weatherDegreeText);
        CurrTemp.setText(String.valueOf(currWeather.temperature) + (char) 0x00B0);

    }

    public void setPastWeather() {
        if (mService.getPastWeather() != null) {
            wAdaptor = new WeatherInfoListAdaptor(this, (ArrayList<WeatherInfo>) mService.getPastWeather());
            weatherList = (ListView) findViewById(R.id.historyList);
            weatherList.setAdapter(wAdaptor);
        }
    }
}
