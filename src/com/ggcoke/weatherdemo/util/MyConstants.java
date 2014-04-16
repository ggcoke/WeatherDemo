package com.ggcoke.weatherdemo.util;

public class MyConstants {
	private static final String LOG_TAG = MyConstants.class.getSimpleName();
	
	public static final String CITY_CODE_COUNTRY = "http://www.weather.com.cn/data/city3jdata/china.html";	// 获取省级区域id
	public static final String CITY_CODE_PROVID = "http://www.weather.com.cn/data/city3jdata/provshi/";		// 获取市级区域id
	public static final String CITY_CODE_CITY_ID = "http:www.weather.com.cn/data/city3jdata/station/";		// 获取县级区域id
	
	public static final String WEATHER_FORECAST = "http://m.weather.com.cn/data/";
	public static final String WEATHER_SK = "http://www.weather.com.cn/data/sk/";
	public static final String WEATHER_CITYINFO = "http://www.weather.com.cn/data/cityinfo/";
	
	public static final String ERROR_INVALIDED_REQUEST = "bad_request";
}
