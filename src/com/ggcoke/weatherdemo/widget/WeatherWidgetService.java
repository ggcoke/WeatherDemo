package com.ggcoke.weatherdemo.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.ggcoke.weatherdemo.util.Utils;
import com.ggcoke.weatherdemo.util.WeatherSharedPreferencesEdit;

import java.util.Date;

/**
 * Created by wanghuisong on 2014/5/8.
 */
public class WeatherWidgetService extends Service {
    private static final String LOG_TAG = WeatherWidgetService.class.getSimpleName();

    private AlarmManager alarm;
    private PendingIntent mPendingIntent;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, WeatherWidgetProvider.class));
        RemoteViews views = WeatherWidgetProvider.getRemoteWeatherViews(this);
        WeatherWidgetProvider.updateWidgetClock(this, views);
        if (isUpdateWeather(this)) {
            WeatherWidgetProvider.updateWidgetWeather(this, views);
        }
        appWidgetManager.updateAppWidget(appWidgetIds, views);

        long updatePeriod = getUpdatePeriod(this);
        PendingIntent pIntent = getUpdateIntent(this, intent);
        alarm = ((AlarmManager) this.getSystemService(Context.ALARM_SERVICE));
        if (Utils.isKitKatOrLater()) {
            alarm.setExact(AlarmManager.RTC_WAKEUP, updatePeriod, pIntent);
        } else {
            alarm.set(AlarmManager.RTC_WAKEUP, updatePeriod, pIntent);
        }

        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        if (alarm != null) {
            alarm.cancel(mPendingIntent);
        }
        super.onDestroy();
    }

    private long getUpdatePeriod(Context context) {
        Date date = new Date();
        long now = date.getTime();
        long unit = 60 * 1000;
        int s = date.getSeconds();
        unit -= s * 1000;
        return now + unit;
    }

    private boolean isUpdateWeather(Context context) {
        long lastUpdateTime = WeatherSharedPreferencesEdit.getInstance(context).getLastUpdateTime();
        long updatePeriod = WeatherSharedPreferencesEdit.getInstance(context).getUpdatePeriod();
        Date date = new Date();
        long now = date.getTime();
        if (lastUpdateTime == 0 || now >= lastUpdateTime + updatePeriod) {
            return true;
        }
        return false;
    }

    private PendingIntent getUpdateIntent(Context context, Intent intent) {
        if (mPendingIntent == null) {
            mPendingIntent = PendingIntent.getService(context, 0, intent, 0);
        }

        return mPendingIntent;
    }
}
