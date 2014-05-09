package com.ggcoke.weatherdemo.util;

import android.content.Context;
import android.content.SharedPreferences;

public class WeatherSharedPreferencesEdit {
    private static final String LOG_TAG = WeatherSharedPreferencesEdit.class.getSimpleName();
    private static SharedPreferences sPreferences;
    private SharedPreferences.Editor editor;
    private static WeatherSharedPreferencesEdit _instance = null;
    
    private WeatherSharedPreferencesEdit(Context ctx) {
        sPreferences = ctx.getSharedPreferences("MySharedPreferencesEdit", Context.MODE_PRIVATE);
        editor = sPreferences.edit();
    }
    
    public static WeatherSharedPreferencesEdit getInstance(Context ctx) {
        if (null == _instance) {
            _instance = new WeatherSharedPreferencesEdit(ctx);
        }
        
        return _instance;
    }


    public void storeSelectedCity(String city) {
        editor.putString("CitySelected", city).commit();
    }
    public String getSelectedCity() {
        return sPreferences.getString("CitySelected", null);
    }

    
    public void setLastUpdateTime(long lastUpdateTime) {
        editor.putLong("LastUpdateTime", lastUpdateTime).commit();
    }
    public long getLastUpdateTime() {
        return sPreferences.getLong("LastUpdateTime", 0);
    }

    public void setUpdatePeriod(long period) {
        editor.putLong("updatePeriod", period).commit();
    }

    public long getUpdatePeriod() {
        return 1*60*1000;
//        return sPreferences.getLong("updatePeriod", 0);
    }
    
    public void setWeatherInfoWithCityCode(String cityCode, String weatherInfo) {
        editor.putString(cityCode, weatherInfo).commit();
    }
    public String getWeatherInfoByCityCode(String cityCode) {
        return sPreferences.getString(cityCode, null);
    }
    
    public void setWeatherIndexWithCityCode(String cityCode, String weatherIndex) {
        editor.putString("index_"+cityCode, weatherIndex);
    }
    public String getWeatherIndexByCityCode(String cityCode) {
        return sPreferences.getString("index_"+cityCode, null);
    }

    public void setHotCities(String hotCities) {
        editor.putString("hot", hotCities);
    }

    public String getHotCities() {
        return "北京-上海-天津-重庆-湖南_长沙-吉林_长春-四川_成都-福建_福州-广东_广州-贵州_贵阳-内蒙古_呼和浩特-黑龙江_哈尔滨-安徽_合肥-浙江_杭州-海南_海口-山东_济南-云南_昆明-西藏_拉萨-甘肃_兰州-广西_南宁-江苏_南京-江西_南昌";
    }
}
