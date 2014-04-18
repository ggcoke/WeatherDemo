package com.ggcoke.weatherdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ggcoke.weatherdemo.R;
import com.ggcoke.weatherdemo.util.MyConstants;
import com.ggcoke.weatherdemo.util.MySharedPreferencesEdit;
import com.ggcoke.weatherdemo.util.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForecastLayout extends LinearLayout{
	private static final String LOG_TAG = ForecastLayout.class.getSimpleName();
	
	private Context mContext;
    private String mCity;
    private String weatherInfo;

    private TextView tvCityName;
    private TextView currentWD;
    private TextView currentFX;
    private TextView currentFL;
    private TextView currentSD;
    private TextView currentUpdateTime;
    private TextView currentAlarm;
    private TextView index;

	public ForecastLayout(final Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public void initView(String city) {
        mCity = city;
		// 初始化控件
        tvCityName = (TextView) findViewById(R.id.tv_city_name);
        currentWD = (TextView) findViewById(R.id.tv_current_wendu);
        currentFX = (TextView) findViewById(R.id.tv_current_fengxiang);
        currentFL = (TextView) findViewById(R.id.tv_current_fengli);
        currentSD = (TextView) findViewById(R.id.tv_current_shidu);
        currentUpdateTime = (TextView) findViewById(R.id.tv_update_time);
        currentAlarm = (TextView) findViewById(R.id.tv_current_alarm);
        index = (TextView) findViewById(R.id.tv_index);
	}

    private void showWeather(){
        try {
            JSONObject obj = new JSONObject(weatherInfo);
            tvCityName.setText(obj.getString("city"));
            currentWD.setText(obj.getString("wendu"));
            currentFX.setText(obj.getString("fengxiang"));
            currentFL.setText(obj.getString("fengli"));
            currentSD.setText(obj.getString("shidu"));
            currentUpdateTime.setText(obj.getString("currentTime") + " " + obj.getString("updatetime"));
            currentAlarm.setText(null == obj.getString("alarm") ? "无":obj.getString("alarm"));

            StringBuilder sb = new StringBuilder();
            JSONArray indexes = obj.getJSONObject("zhishus").getJSONArray("zhishu");
            for (int i = 0; i < indexes.length(); i++) {
                JSONObject info = (JSONObject) indexes.get(i);
                sb.append(info.getString("name"))
                        .append(":")
                        .append(info.getString("value"))
                        .append(",")
                        .append(info.getString("detail"))
                        .append("\r\n<br/>");
            }
        } catch (JSONException e) {
            Toast.makeText(mContext, "解析天气数据失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

	public void setWeatherInfo(final String city) {
        weatherInfo = MySharedPreferencesEdit.getInstance(mContext).getWeatherInfoByCityCode(city);
		if (null != weatherInfo && weatherInfo.length() > 0) {
            showWeather();
		}
		
		if (NetworkUtil.netWorkAvilable(mContext)) {
            AsyncHttpClient clientCurrentWeather = new AsyncHttpClient();
            clientCurrentWeather.get(MyConstants.WEATHER_LETV_URL + city, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONObject response) {
                    MySharedPreferencesEdit.getInstance(mContext).setWeatherInfoWithCityCode(city, response.toString());
                    super.onSuccess(response);
                }

                @Override
                public void onFailure(Throwable error) {
                    Toast.makeText(mContext, "获取实时天气情况失败，请重试", Toast.LENGTH_SHORT).show();
                }
            });
		}
	}
}
