package com.ggcoke.weatherdemo.util;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferencesEdit {
	private static final String LOG_TAG = MySharedPreferencesEdit.class.getSimpleName();
	private static SharedPreferences sPreferences;
	private SharedPreferences.Editor editor;
	private static MySharedPreferencesEdit _instance = null;
	
	private MySharedPreferencesEdit(Context ctx) {
		sPreferences = ctx.getSharedPreferences("MySharedPreferencesEdit", Context.MODE_PRIVATE);
		editor = sPreferences.edit();
	}
	
	public static MySharedPreferencesEdit getInstance(Context ctx) {
		if (null == _instance) {
			_instance = new MySharedPreferencesEdit(ctx);
		}
		
		return _instance;
	}
	
	/**
	 * 存储用户选择的城市列表，格式为code1_name1-code2_name2
	 * @param CityCodes
	 */
	public void setCityCodes(String CityCodes) {
		editor.putString("CityCodes", CityCodes).commit();
	}
	public String getCityCodes() {
//		return sPreferences.getString("CityCodes", null);
        return "朝阳_101010300-藁城_101090115";
	}
	
	public void setLastUpdateTime(long LastUpdateTime) {
		editor.putLong("LastUpdateTime", LastUpdateTime).commit();
	}
	public long getLastUpdateTime() {
		return sPreferences.getLong("LastUpdateTime", 0);
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
}
