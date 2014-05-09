package com.ggcoke.weatherdemo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.ggcoke.weatherdemo.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static boolean isNetworkAvilable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos != null) {
                for (int i = 0, count = networkInfos.length; i < count; i++) {
                    if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String getWebContent(String url) {
        HttpGet request = new HttpGet(url);
        HttpParams params=new BasicHttpParams();
        HttpClient httpClient = new DefaultHttpClient(params);
        try{
            HttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity());
                return content;
            } else {
                return null;
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return null;
    }

    public static boolean isKitKatOrLater() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public static int getWeatherBitMapResource(String weather) {
        if (weather.equals("晴")) {
            return R.drawable.weathericon_condition_01;
        } else if (weather.equals("多云")) {
            return R.drawable.weathericon_condition_02;
        } else if (weather.equals("阴")) {
            return R.drawable.weathericon_condition_04;
        } else if (weather.equals("雾")) {
            return R.drawable.weathericon_condition_05;
        } else if (weather.equals("沙尘暴")) {
            return R.drawable.weathericon_condition_06;
        } else if (weather.equals("阵雨")) {
            return R.drawable.weathericon_condition_07;
        } else if (weather.equals("小雨") || weather.equals("小到中雨")) {
            return R.drawable.weathericon_condition_08;
        } else if (weather.equals("大雨")) {
            return R.drawable.weathericon_condition_09;
        } else if (weather.equals("雷阵雨")) {
            return R.drawable.weathericon_condition_10;
        } else if (weather.equals("小雪")) {
            return R.drawable.weathericon_condition_11;
        } else if (weather.equals("大雪")) {
            return R.drawable.weathericon_condition_12;
        } else if (weather.equals("雨夹雪")) {
            return R.drawable.weathericon_condition_13;
        } else {
            return R.drawable.weathericon_condition_17;
        }
    }

    public static String getWeek(Date date, int position) {
        String Week = "星期";
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }

        return Week;
    }

    public static boolean isNight(String time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Calendar now = Calendar.getInstance();
        Calendar night = Calendar.getInstance();
        try {
            now.setTime(format.parse(time));
            night.setTime(format.parse(WeatherConstants.WEATHER_NIGHT_TIME));

            return now.after(night);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
}
