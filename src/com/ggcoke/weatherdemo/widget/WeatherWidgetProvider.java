package com.ggcoke.weatherdemo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.ggcoke.weatherdemo.R;
import com.ggcoke.weatherdemo.activity.MainActivity;
import com.ggcoke.weatherdemo.util.Utils;
import com.ggcoke.weatherdemo.util.WeatherConstants;
import com.ggcoke.weatherdemo.util.WeatherSharedPreferencesEdit;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wanghuisong on 2014/5/8.
 */
public class WeatherWidgetProvider extends AppWidgetProvider {
    private static final String LOG_TAG = WeatherWidgetProvider.class.getSimpleName();

    @Override
    public void onEnabled(Context context) {
        context.startService(new Intent(context, WeatherWidgetService.class));
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        context.stopService(new Intent(context, WeatherWidgetService.class));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    public static RemoteViews getRemoteWeatherViews(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_4x1);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.rl_widgets, pendingIntent);
        return views;
    }

    public static void updateWidgetWeather(final Context context, RemoteViews views) {
        String selectedCity = WeatherSharedPreferencesEdit.getInstance(context).getSelectedCity();
        if (selectedCity == null || selectedCity.length() <= 0) {
            views.setViewVisibility(R.id.ll_widget_info, View.GONE);
            views.setViewVisibility(R.id.tv_widget_no_city, View.VISIBLE);
            views.setTextViewText(R.id.tv_widget_no_city, context.getResources().getString(R.string.widget_no_city));
        } else {
            Date date = new Date();
            long now = date.getTime();
            WeatherSharedPreferencesEdit.getInstance(context).setLastUpdateTime(now);

            String cityInfo = selectedCity.split(WeatherConstants.SPLIT_STRING_CITY)[0];
            final String[] infos = cityInfo.split(WeatherConstants.SPLIT_STRING_INFO);
            String weather = WeatherSharedPreferencesEdit.getInstance(context).getWeatherInfoByCityCode(infos[infos.length-1]);
            if (weather != null) {
                showWidgetWeather(context, views, weather);
            }
            if (Utils.isNetworkAvilable(context)) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(WeatherConstants.WEATHER_LETV_URL + infos[infos.length - 1], new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(JSONObject response) {
                        WeatherSharedPreferencesEdit.getInstance(context).setWeatherInfoWithCityCode(infos[infos.length-1], response.toString());
                        super.onSuccess(response);
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        Toast.makeText(context, "获取实时天气情况失败，请重试", Toast.LENGTH_SHORT).show();
                        super.onFailure(error);
                    }
                });
            }
        }
    }

    public static void updateWidgetClock(Context context, RemoteViews views) {
        views.setViewVisibility(R.id.tv_widget_no_city, View.GONE);
        views.setViewVisibility(R.id.ll_widget_info, View.VISIBLE);
        Date date = new Date();
        SimpleDateFormat foramt = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat foramt2 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String timeText = foramt.format(date);
        views.setTextViewText(R.id.tv_widget_time, timeText);
        views.setTextViewText(R.id.tv_widget_day, foramt2.format(date) + " " + Utils.getWeek(date, 0));
    }

    public static void showWidgetWeather(Context context, RemoteViews views, String data) {
        views.setViewVisibility(R.id.tv_widget_no_city, View.GONE);
        views.setViewVisibility(R.id.ll_widget_info, View.VISIBLE);

        try {
            JSONObject obj = new JSONObject(data);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String now = format.format(date);

            JSONObject jsonForecast = obj.getJSONObject("forecast");
            JSONArray forecasts = jsonForecast.getJSONArray("weather");
            if (forecasts == null || forecasts.length() <= 0) {
                return;
            }
            JSONObject weatherInfo = null;
            JSONObject weatherToday = forecasts.getJSONObject(0);
            if (Utils.isNight(now)) {
                weatherInfo = weatherToday.getJSONObject("night");
            } else {
                weatherInfo = weatherToday.getJSONObject("day");
            }

            views.setTextViewText(R.id.tv_widget_city, obj.getString("city"));
            views.setTextViewText(R.id.tv_widget_temp_now, obj.getString("wendu"));
            views.setTextViewText(R.id.tv_widget_temp_today, weatherToday.getString("high") + "/" + weatherToday.getString("low"));
            views.setTextViewText(R.id.tv_widget_desc_today, weatherInfo.getString("type"));
            views.setImageViewResource(R.id.iv_widget_img_today, Utils.getWeatherBitMapResource(weatherInfo.getString("type")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
