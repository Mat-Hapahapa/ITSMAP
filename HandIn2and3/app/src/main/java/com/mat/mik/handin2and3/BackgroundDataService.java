package com.mat.mik.handin2and3;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
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

public class BackgroundDataService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private static final String TAG = BackgroundDataService.class.getSimpleName();
    private static final String API_STR = "http://api.openweathermap.org/data/2.5/weather?q=Aarhus&appid=c9a48f3c4c3462b1d0658ffdf958f8e3";
    private static final String DBNAME = "weatherDb";
    private DatabaseManager dbManager;
    private boolean started = false;


    public class LocalBinder extends Binder {
        BackgroundDataService getService() {
            return BackgroundDataService.this;
        }
    }

    public BackgroundDataService() {
        DatabaseManager dManager = new DatabaseManager(this, DBNAME, null, 1);
    }



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!started && intent != null) {
            Log.d(TAG, "Background service started");
            started = true;
            new UrlTask().execute(API_STR);
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
    return mBinder;

    }


    private class UrlTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            WeatherInfo wInfo = new WeatherInfo(urlCall(params[0]));
            dbManager.addWeatherInfo(wInfo);

            return urlCall(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
        }
    }

    /* Note: documentation from:
    * http://developer.android.com/reference/java/net/HttpURLConnection.html#setRequestMethod(java.lang.String)
    * */
    private String urlCall(String ConncetionStr) {

        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(ConncetionStr);
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

