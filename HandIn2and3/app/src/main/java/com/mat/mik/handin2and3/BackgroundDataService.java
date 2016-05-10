package com.mat.mik.handin2and3;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.BaseAdapter;

import com.mat.mik.handin2and3.Managers.DatabaseManager;
import com.mat.mik.handin2and3.Models.WeatherInfo;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BackgroundDataService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private static final String TAG = BackgroundDataService.class.getSimpleName();
    private static final String API_STR = "http://api.openweathermap.org/data/2.5/weather?q=Aarhus&appid=c9a48f3c4c3462b1d0658ffdf958f8e3";
    private static final String DBNAME = "weatherDb";
    private DatabaseManager dbManager;
    private boolean started = false;
    private UrlTask mUrlTask;

    public class LocalBinder extends Binder {
        BackgroundDataService getService() {
            return BackgroundDataService.this;
        }
    }

    public BackgroundDataService() {

    }


    public WeatherInfo getCurrentWeather() {
        if (!dbManager.getWeatherInfo().isEmpty()) {
            return dbManager.getWeatherInfo().get(0);
        }
        return new WeatherInfo("NA", 404);
    }

    public List<WeatherInfo> getPastWeather() {
        ArrayList<WeatherInfo> tmpList = dbManager.getWeatherInfo();
        int listSize = tmpList.size();

        if (tmpList.size() > 1) {
            if (listSize > 49) { listSize = 49;}
            return new ArrayList<>(tmpList.subList(1, listSize));
        }
        else {return null;}
    }


    @Override
    public void onCreate() {
        super.onCreate();
        dbManager = new DatabaseManager(this, DBNAME, null, 1);
    }


    @Override
    public IBinder onBind(Intent intent) {

        if (!started && intent != null) {
            Log.d(TAG, "Background service started");
            started = true;
            mUrlTask = new UrlTask();
            mUrlTask.execute(API_STR);
        }
        return mBinder;

    }


    private class UrlTask extends AsyncTask<String, Void, Void> {

        private Timer timer = new Timer();

        @Override
        protected Void doInBackground(String... params) {

            timer.schedule(new timerTasks(),0 , 1800000 );
            return null;
        }
    }


    private class timerTasks extends TimerTask{

        @Override
        public void run() {
            WeatherInfo wInfo = new WeatherInfo(urlCall(API_STR));
            dbManager.addWeatherInfo(wInfo);

            Intent intent = new Intent(getApplicationContext().getResources().getString(R.string.new_data));
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }

    private String urlCall(String ConnectionStr) {
    /* Note: documentation from:
    * http://developer.android.com/reference/java/net/HttpURLConnection.html#setRequestMethod(java.lang.String)
    * */
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(ConnectionStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setDoInput(true);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private String readStream(InputStream in) {
        try {
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            int i = in.read();
            while (i != -1) {
                boas.write(i);
                i = in.read();
            }
            return boas.toString();
        } catch (IOException e) {
            return "";
        }
    }

    }
}

