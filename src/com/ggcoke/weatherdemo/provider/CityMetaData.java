package com.ggcoke.weatherdemo.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ggcoke on 14-5-6.
 */
public final class CityMetaData {
    public static final String AUTHORITY = "com.ggcoke.weatherdemo.city";
    private CityMetaData(){}

    public static final class City implements BaseColumns {
        private City() {}

        public static final String TABLE_NAME = "city";
        private static final String SCHEME = "content://";
        private static final String PATH_CITY = "/city";
        private static final String PATH_CITY_NAME = "/city/";
        public static final int CITY_NAME_PATH_POSITION = 1;

        public static final Uri CONTENT_NAME_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_CITY_NAME);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.ggcoke.city";

        public static final String COLUMN_NAME_CODE = "code";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PHONETIC = "phonetic";
        public static final String COLUMN_NAME_PROVINCE = "province";
        public static final String COLUMN_NAME_DISTRICT = "district";
        public static final String COLUMN_NAME_LEVEL = "level";

    }
}
