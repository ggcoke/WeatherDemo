package com.ggcoke.weatherdemo.util;

public class WeatherConstants {
    private static final String LOG_TAG = WeatherConstants.class.getSimpleName();
    public static final String WEATHER_LETV_URL = "http://115.182.94.28/iptv/api/box/newWeatherinfo.json?city=";
    
    public static final int INTENT_CODE_SUCCESS = 0x0000;
    public static final int INTENT_CODE_ADD_CITY = 0x0001;

    public static final int AUTO_LOCATION_FAILED = 0x2;
    public static final int AUTO_LOCATION_SUCCESS = 0x1;

    public static final String BD_LOCATION_KEY = "l2wcju6pnTxSIEQshK9AhbRZ";
}

