package com.mat.mik.handin2and3.Adaptors;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.mat.mik.handin2and3.Models.WeatherInfo;
import com.mat.mik.handin2and3.R;

import java.util.ArrayList;

/**
 * Created by matry on 05-05-2016.
 */
public class WeatherInfoListAdaptor extends BaseAdapter {

    Context context;
    ArrayList<WeatherInfo> weatherInfoList;
    WeatherInfo wInfo;

    public WeatherInfoListAdaptor(Context context, ArrayList<WeatherInfo> weatherList) {
        this.context = context;
        this.weatherInfoList = weatherList;
    }

    @Override
    public int getCount() {
        if (weatherInfoList != null) {
            return weatherInfoList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (weatherInfoList != null) {
            return weatherInfoList.get(position);
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater demoInflator = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = demoInflator.inflate(R.layout.weather_item, null);
        }

        //ToDo: Set image ud fra vejret

        wInfo = weatherInfoList.get(position);
        if (wInfo != null) {
            TextView description = (TextView) convertView.findViewById(R.id.wheatherDesc);
            description.setText(wInfo.description);

            TextView degrees = (TextView) convertView.findViewById(R.id.degrees);
            degrees.setText(String.valueOf(wInfo.temperature) + (char) 0x00B0);

            setWeatherPicture(convertView, wInfo.description);
        }

        return convertView;


    }

    private void setWeatherPicture(View convertView, String weatherDesc) {
        WeatherIconView weatherIconView;
        weatherIconView = (WeatherIconView) convertView.findViewById(R.id.weatherIcon);

        if (weatherDesc != null) {
            switch (weatherDesc) {
                case "clear sky":
                    weatherIconView.setIconResource(this.context.getString(R.string.wi_day_sunny));
                    break;
                case "few clouds":
                    weatherIconView.setIconResource(this.context.getString(R.string.wi_day_cloudy));
                    break;
                case "scattered clouds":
                    weatherIconView.setIconResource(this.context.getString(R.string.wi_cloud));
                    break;
                case "broken clouds":
                    weatherIconView.setIconResource(this.context.getString(R.string.wi_cloudy));
                    break;
                case "shower rain":
                    weatherIconView.setIconResource(this.context.getString(R.string.wi_showers));
                    break;
                case "rain":
                    weatherIconView.setIconResource(this.context.getString(R.string.wi_rain));
                    break;
                case "thunderstorm":
                    weatherIconView.setIconResource(this.context.getString(R.string.wi_thunderstorm));
                    break;
                case "snow":
                    weatherIconView.setIconResource(this.context.getString(R.string.wi_snow));
                    break;
                case "mist":
                    weatherIconView.setIconResource(this.context.getString(R.string.wi_fog));
                    break;
                default:
                    weatherIconView.setIconResource(this.context.getString(R.string.wi_na));
                    break;
            }
        }else {weatherIconView.setIconResource(this.context.getString(R.string.wi_na));}

    }
}
