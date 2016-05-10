package com.mat.mik.handin2and3;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.mat.mik.handin2and3.Adaptors.WeatherInfoListAdaptor;
import com.mat.mik.handin2and3.Models.WeatherInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {

    private ListView weatherList;
    private WeatherInfoListAdaptor wAdaptor;
    BackgroundDataService mService;
    private WeatherInfo currWeather;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(getString(R.string.new_data)));

        FloatingActionButton fap = (FloatingActionButton) findViewById(R.id.fapBtn);
        fap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentWeather();
                setPastWeather();
            }
        });
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

        if (currWeather.description != null) {
            setCurrentImg(currWeather.description);

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
    /* Change icon to match current weather - from project: https://github.com/pwittchen/WeatherIconView */
    public void setCurrentImg (String wDesc){
        WeatherIconView weatherIconView;
        weatherIconView = (WeatherIconView) findViewById(R.id.weatherImg);
        if (wDesc != null) {
            switch (wDesc) {
                case "clear sky":
                    weatherIconView.setIconResource(getString(R.string.wi_day_sunny));
                    break;
                case "few clouds":
                    weatherIconView.setIconResource(getString(R.string.wi_day_cloudy));
                    break;
                case "scattered clouds":
                    weatherIconView.setIconResource(getString(R.string.wi_cloud));
                    break;
                case "broken clouds":
                    weatherIconView.setIconResource(getString(R.string.wi_cloudy));
                    break;
                case "shower rain":
                    weatherIconView.setIconResource(getString(R.string.wi_showers));
                    break;
                case "rain":
                    weatherIconView.setIconResource(getString(R.string.wi_rain));
                    break;
                case "thunderstorm":
                    weatherIconView.setIconResource(getString(R.string.wi_thunderstorm));
                    break;
                case "snow":
                    weatherIconView.setIconResource(getString(R.string.wi_snow));
                    break;
                case "mist":
                    weatherIconView.setIconResource(getString(R.string.wi_fog));
                    break;
                default:
                    weatherIconView.setIconResource(getString(R.string.wi_na));
                    break;
            }
        } else {
            weatherIconView.setIconResource(getString(R.string.wi_na));
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                setCurrentWeather();
                setPastWeather();
            Log.d(TAG,"Update Received from service");
        }
    };

}
