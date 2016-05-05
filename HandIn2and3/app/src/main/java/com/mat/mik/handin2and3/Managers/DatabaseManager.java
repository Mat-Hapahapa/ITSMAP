package com.mat.mik.handin2and3.Managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Mikkel on 05-05-2016.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WeatherInfo.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + Contract.WeatherInfoEntry.TABLE_NAME + " (" + Contract.WeatherInfoEntry.COLUMN_ID
            + " LONG PRIMARY KEY" + Contract.WeatherInfoEntry.COLUMN_DESCRIPTION +
            TEXT_TYPE + COMMA_SEP + Contract.WeatherInfoEntry.COLUMN_TEMPERATURE +
            TEXT_TYPE + COMMA_SEP + Contract.WeatherInfoEntry.COLUMN_TIMESTAMP +
            TEXT_TYPE + COMMA_SEP + " )";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " +
            Contract.WeatherInfoEntry.TABLE_NAME;

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
