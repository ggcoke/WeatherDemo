package com.ggcoke.weatherdemo.util;

public class WeatherConstants {
    private static final String LOG_TAG = WeatherConstants.class.getSimpleName();
    
    public static final String CITY_CODE_COUNTRY = "http://www.weather.com.cn/data/city3jdata/china.html";    // 获取省级区域id
    public static final String CITY_CODE_PROVID = "http://www.weather.com.cn/data/city3jdata/provshi/";        // 获取市级区域id
    public static final String CITY_CODE_CITY_ID = "http:www.weather.com.cn/data/city3jdata/station/";        // 获取县级区域id
    
    public static final String WEATHER_FORECAST = "http://m.weather.com.cn/data/";
    public static final String WEATHER_SK = "http://www.weather.com.cn/data/sk/";
    public static final String WEATHER_CITYINFO = "http://www.weather.com.cn/data/cityinfo/";

    public static final String WEATHER_LETV_URL = "http://115.182.94.28/iptv/api/box/newWeatherinfo.json?city=";
    
    public static final String ERROR_INVALIDED_REQUEST = "bad_request";

    public static final int INTENT_CODE_SUCCESS = 0x0000;
    public static final int INTENT_CODE_ADD_CITY = 0x0001;

    public static final int AUTO_LOCATION_FAILED = 0x2;
    public static final int AUTO_LOCATION_SUCCESS = 0x1;

    public static final int AUTO_LOCATION_RETRY_SLEEP = 1000;
    public static final int AUTO_LOCATION_RETRY_TIMES = 5;

//    public static final String BD_LOCATION_KEY = "l2wcju6pnTxSIEQshK9AhbRZ";
    public static final String BD_LOCATION_KEY = "697f50541f8d4779124896681cb6584d";
}

