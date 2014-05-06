package com.ggcoke.weatherdemo.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.ggcoke.weatherdemo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by ggcoke on 14-5-6.
 */
public class WeatherCityProvider extends ContentProvider {
    private static final String LOG_TAG = WeatherCityProvider.class.getSimpleName();
    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;

    private static HashMap<String, String> sCityProjectionMap;
    private static final String[] READ_CITY_PROJECTION = new String[] {
            CityMetaData.City._ID,
            CityMetaData.City.COLUMN_NAME_CODE,
            CityMetaData.City.COLUMN_NAME_NAME,
            CityMetaData.City.COLUMN_NAME_PHONETIC,
            CityMetaData.City.COLUMN_NAME_PROVINCE,
            CityMetaData.City.COLUMN_NAME_DISTRICT,
            CityMetaData.City.COLUMN_NAME_LEVEL
    };

    private static final int READ_CITY_NAME_INDEX = 1;

    private static final int CITY_NAME = 1;

    private static UriMatcher sUriMatcher;
    private DatabaseHelper mOpenHelper;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(CityMetaData.AUTHORITY, "city/*", CITY_NAME);

        sCityProjectionMap = new HashMap<String, String>();
        sCityProjectionMap.put(CityMetaData.City._ID, CityMetaData.City._ID);
        sCityProjectionMap.put(CityMetaData.City.COLUMN_NAME_CODE, CityMetaData.City.COLUMN_NAME_CODE);
        sCityProjectionMap.put(CityMetaData.City.COLUMN_NAME_NAME, CityMetaData.City.COLUMN_NAME_NAME);
        sCityProjectionMap.put(CityMetaData.City.COLUMN_NAME_PHONETIC, CityMetaData.City.COLUMN_NAME_PHONETIC);
        sCityProjectionMap.put(CityMetaData.City.COLUMN_NAME_PROVINCE, CityMetaData.City.COLUMN_NAME_PROVINCE);
        sCityProjectionMap.put(CityMetaData.City.COLUMN_NAME_DISTRICT, CityMetaData.City.COLUMN_NAME_DISTRICT);
        sCityProjectionMap.put(CityMetaData.City.COLUMN_NAME_LEVEL, CityMetaData.City.COLUMN_NAME_LEVEL);
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (sUriMatcher.match(uri)) {
            case CITY_NAME:
                String cityName = uri.getPathSegments().get(CityMetaData.City.CITY_NAME_PATH_POSITION);
                cursor = db.rawQuery("SELECT c1.name, c2.name, c3.name FROM city c1, city c2, city c3 WHERE c1._id = c3.province AND c2._id = c3.district AND c3.name LIKE ? AND c3.level = ? ORDER BY c3.province ASC", new String[]{"%" + cityName + "%", "3"});
                break;
            default:
                throw new IllegalArgumentException("Unknow URI" + uri);
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case CITY_NAME:
                return CityMetaData.City.CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknow URI" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    static class DatabaseHelper extends SQLiteOpenHelper {
        private Context context;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + CityMetaData.City.TABLE_NAME + "("
                    + CityMetaData.City._ID + " INTEGER PRIMARY KEY NOT NULL,"
                    + CityMetaData.City.COLUMN_NAME_CODE + " TEXT NOT NULL,"
                    + CityMetaData.City.COLUMN_NAME_NAME + " TEXT NOT NULL,"
                    + CityMetaData.City.COLUMN_NAME_PHONETIC + " TEXT,"
                    + CityMetaData.City.COLUMN_NAME_PROVINCE + " INTEGER DEFAULT NULL,"
                    + CityMetaData.City.COLUMN_NAME_DISTRICT + " INTEGER DEFAULT NULL,"
                    + CityMetaData.City.COLUMN_NAME_LEVEL + " INTEGER DEFAULT NULL"
                    + ")"
            );

            setCityInfo(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i2) {
            db.execSQL("DROP TABLE IF EXISTS " + CityMetaData.City.TABLE_NAME);
            onCreate(db);
        }

        private void setCityInfo(SQLiteDatabase db) {
            InputStream is = this.context.getResources().openRawResource(R.raw.city_info);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    db.execSQL(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
