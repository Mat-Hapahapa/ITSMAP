package com.mat.mik.handin2and3.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.mat.mik.handin2and3.Models.WeatherInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Mikkel on 05-05-2016. a
 */

/**
 * This class was heavily inspired by the examples from this link
 * http://developer.android.com/training/basics/data-storage/databases.html
 * Therefore similarities might occur.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String TAG = DatabaseManager.class.getSimpleName();
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WeatherInfo.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + Contract.WeatherInfoEntry.TABLE_NAME + " (" + Contract.WeatherInfoEntry.COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP + Contract.WeatherInfoEntry.COLUMN_DESCRIPTION +
            TEXT_TYPE + COMMA_SEP + Contract.WeatherInfoEntry.COLUMN_TEMPERATURE +
            TEXT_TYPE + COMMA_SEP + Contract.WeatherInfoEntry.COLUMN_TIMESTAMP +
            TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
            Contract.WeatherInfoEntry.TABLE_NAME;

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public Long addWeatherInfo(WeatherInfo weatherInfo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Contract.WeatherInfoEntry.COLUMN_DESCRIPTION, weatherInfo.description);
        values.put(Contract.WeatherInfoEntry.COLUMN_TEMPERATURE, weatherInfo.temperature);
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timeStampString = format.format(weatherInfo.timestamp);
        values.put(Contract.WeatherInfoEntry.COLUMN_TIMESTAMP,timeStampString);

        long id = db.insert(Contract.WeatherInfoEntry.TABLE_NAME, null, values);
        if(id == -1) {
            //TODO: Errorhandling
            Log.d(TAG, "Couldn't insert weatherinfo");
        }else
        {
            weatherInfo.id = id;
        }
        return id;
    }

    public ArrayList<WeatherInfo> getWeatherInfo(){

        SQLiteDatabase db = getReadableDatabase();
        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                Contract.WeatherInfoEntry.COLUMN_ID,
                Contract.WeatherInfoEntry.COLUMN_DESCRIPTION,
                Contract.WeatherInfoEntry.COLUMN_TEMPERATURE,
                Contract.WeatherInfoEntry.COLUMN_TIMESTAMP
        };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                Contract.WeatherInfoEntry.COLUMN_ID + " DESC";

        Cursor c = db.query(
                Contract.WeatherInfoEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        c.moveToFirst();
        ArrayList<WeatherInfo> weatherInfoList = new ArrayList<>();
        while(c.moveToNext()) {
            WeatherInfo info = new WeatherInfo();

            String timestampString = c.getString(c.getColumnIndex(
                    Contract.WeatherInfoEntry.COLUMN_TIMESTAMP));
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            try
            {
                info.timestamp = format.parse(timestampString);
            } catch (ParseException ex) {
                Log.e(TAG, ex.toString());
            }
            info.id = c.getLong(c.getColumnIndex(
                    Contract.WeatherInfoEntry.COLUMN_ID));
            info.temperature = c.getDouble(c.getColumnIndex(
                    Contract.WeatherInfoEntry.COLUMN_TEMPERATURE));
            info.description = c.getString(c.getColumnIndex(Contract.WeatherInfoEntry.COLUMN_DESCRIPTION));
            weatherInfoList.add(info);
        }
        return weatherInfoList;
    }

    public final class Contract {

        public Contract() {}

        public abstract class WeatherInfoEntry implements BaseColumns {
            public static final String TABLE_NAME = "weather_info";
            public static final String COLUMN_ID = "id";
            public static final String COLUMN_DESCRIPTION = "description";
            public static final String COLUMN_TEMPERATURE = "temperature";
            public static final String COLUMN_TIMESTAMP = "timestamp";
        }
    }
}
