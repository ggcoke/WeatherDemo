package com.ggcoke.weatherdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ggcoke.weatherdemo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by wanghuisong on 2014/4/22.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = DBHelper.class.getSimpleName();
    private static final String DB_NAME = "weather.db";
    private static final int VERSION = 1;

    private SQLiteDatabase mDatabase;
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable(sqLiteDatabase);
        setCityInfo(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    }

    private void createTable(SQLiteDatabase db) {
        String sqlCreateTable = "CREATE TABLE city (" +
                "id INTEGER PRIMARY KEY NOT NULL," +
                "code TEXT NOT NULL," +
                "name TEXT NOT NULL," +
                "phonetic TEXT DEFAULT NULL," +
                "province INTEGER DEFAULT NULL," +
                "district INTEGER DEFAULT NULL," +
                "level INTEGER DEFAULT NULL)";
        db.execSQL(sqlCreateTable);
    }

    private void setCityInfo(SQLiteDatabase db) {
        InputStream is = this.mContext.getResources().openRawResource(R.raw.city_info);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        try {
            while ((line = br.readLine()) != null) {
                db.execSQL(line);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
