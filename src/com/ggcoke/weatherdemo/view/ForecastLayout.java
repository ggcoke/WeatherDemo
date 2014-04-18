package com.ggcoke.weatherdemo.view;

import com.ggcoke.weatherdemo.R;
import com.ggcoke.weatherdemo.util.MyConstants;
import com.ggcoke.weatherdemo.util.MySharedPreferencesEdit;
import com.ggcoke.weatherdemo.util.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ForecastLayout extends LinearLayout{
	private static final String LOG_TAG = ForecastLayout.class.getSimpleName();
	
	private Context mContext;
	private String cityName;
	private String cityCode;
	
	/* 实时天气情况 */
	private String weatherCurrent;		// json格式实时天气情况，用来缓存
	private TextView currentCityName;	// 城市名称
	private TextView currentCityCode;	// 城市编码
	private TextView currentTemp;		// 温度
	private TextView currentWD;			// 风向
	private TextView currentWS;			// 风速
	private TextView currentSD;			// 湿度
	private TextView currentWSE;		// 不知道
	private TextView publishTime;		// 发布时间
	
	/* 未来六天天气情况 */
	private TextView temp1;
	private TextView temp2;
	private TextView temp3;
	private TextView temp4;
	private TextView temp5;
	private TextView temp6;
	
	/* 各种指数 */
	private String weatherIndex;	// json格式天气指数，用来缓存
	private TextView index;			// 体感温度
	private TextView index_d;		// 穿衣指数
	private TextView index48;		// 48小时体感温度
	private TextView index48_d;		// 48小时穿衣指数
	private TextView index_uv;		// 紫外线指数
	private TextView index48_uv;	// 48小时紫外线指数
	private TextView index_xc;		// 洗车指数
	private TextView index_tr;		// 出游指数
	private TextView index_co;		// 舒适指数
	private TextView index_cl;		// 晨练指数
	private TextView index_ls;		// 晾晒指数
	private TextView index_ag;		// 感冒指数
	
	
	public ForecastLayout(final Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}
	
	/**
	 * 
	 * @param cityInfo 城市信息，格式为name_code
	 */
	public void initView(String cityInfo) {
		cityName = cityInfo.split("_")[0];
		cityCode = cityInfo.split("_")[1];
		
		// 初始化控件
		currentCityName = (TextView) findViewById(R.id.tv_city_name);
		currentCityCode = (TextView) findViewById(R.id.tv_city_code);
		currentTemp = (TextView) findViewById(R.id.tv_current_temp);
		currentWD = (TextView) findViewById(R.id.tv_current_wd);
		currentWS = (TextView) findViewById(R.id.tv_current_ws);
		currentSD = (TextView) findViewById(R.id.tv_current_sd);
		currentWSE = (TextView) findViewById(R.id.tv_current_wse);
		publishTime = (TextView) findViewById(R.id.tv_current_publish_time);
		
		index = (TextView) findViewById(R.id.tv_forecast_index);
		index_d = (TextView) findViewById(R.id.tv_forecast_index_d);
		index48 = (TextView) findViewById(R.id.tv_forecast_index48);
		index48_d = (TextView) findViewById(R.id.tv_forecast_index48_d);
		index_uv = (TextView) findViewById(R.id.tv_forecast_index_uv);
		index48_uv = (TextView) findViewById(R.id.tv_forecast_index48_uv);
		index_xc = (TextView) findViewById(R.id.tv_forecast_index_xc);
		index_tr = (TextView) findViewById(R.id.tv_forecast_index_tr);
		index_co = (TextView) findViewById(R.id.tv_forecast_index_co);
		index_cl = (TextView) findViewById(R.id.tv_forecast_index_cl);
		index_ls = (TextView) findViewById(R.id.tv_forecast_index_ls);
		index_ag = (TextView) findViewById(R.id.tv_forecast_index_ag);
	}

    private void showWeatherCurrent(){
        try {
            JSONObject obj = new JSONObject(weatherCurrent).getJSONObject("weatherinfo");
            currentCityName.setText(obj.getString("city"));
            currentCityCode.setText(obj.getString("cityid"));
            currentTemp.setText(obj.getString("temp"));
            currentWD.setText(obj.getString("WD"));
            currentWS.setText(obj.getString("WS"));
            currentSD.setText(obj.getString("SD"));
            currentWSE.setText(obj.getString("WSE"));
            publishTime.setText(obj.getString("time"));
        } catch (JSONException e) {
            Toast.makeText(mContext, "解析实时天气数据失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    private void showWeatherIndex() {
        try {
            JSONObject obj = new JSONObject(weatherIndex).getJSONObject("weatherinfo");
            index.setText(obj.getString("index"));
            index_d.setText(obj.getString("index_d"));
            index48.setText(obj.getString("index48"));
            index48_d.setText(obj.getString("index48_d"));
            index_uv.setText(obj.getString("index_uv"));
            index48_uv.setText(obj.getString("index48_uv"));
            index_xc.setText(obj.getString("index_xc"));
            index_tr.setText(obj.getString("index_tr"));
            index_co.setText(obj.getString("index_co"));
            index_cl.setText(obj.getString("index_cl"));
            index_ls.setText(obj.getString("index_ls"));
            index_ag.setText(obj.getString("index_ag"));
        } catch (JSONException e) {
            Toast.makeText(mContext, "解析天气指数数据失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

	public void setWeatherInfo(final String cityCode) {
		weatherCurrent = MySharedPreferencesEdit.getInstance(mContext).getWeatherInfoByCityCode(cityCode);
		if (null != weatherCurrent && weatherCurrent.length() > 0) {
			showWeatherCurrent();
		}
		
		weatherIndex = MySharedPreferencesEdit.getInstance(mContext).getWeatherIndexByCityCode(cityCode);
		if (null != weatherIndex && weatherIndex.length() > 0) {
			showWeatherIndex();
		}
		
		if (NetworkUtil.netWorkAvilable(mContext)) {
            AsyncHttpClient clientCurrentWeather = new AsyncHttpClient();
            clientCurrentWeather.get(MyConstants.WEATHER_SK + cityCode + ".html", new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONObject response) {
                    MySharedPreferencesEdit.getInstance(mContext).setWeatherInfoWithCityCode(cityCode, response.toString());
                    super.onSuccess(response);
                }

                @Override
                public void onFailure(Throwable error) {
                    Toast.makeText(mContext, "获取实时天气情况失败，请重试", Toast.LENGTH_SHORT).show();
                }
            });

            AsyncHttpClient clientCurrentIndex = new AsyncHttpClient();
            clientCurrentIndex.get(MyConstants.WEATHER_FORECAST + cityCode + ".html", new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONObject response) {
                    MySharedPreferencesEdit.getInstance(mContext).setWeatherIndexWithCityCode(cityCode, response.toString());
                    super.onSuccess(response);
                }

                @Override
                public void onFailure(Throwable error) {
                    Toast.makeText(mContext, "获取天气指数失败，请重试", Toast.LENGTH_SHORT).show();
                }
            });
		}
	}
}
