package com.ggcoke.weatherdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForecastLayout extends LinearLayout{
	private static final String LOG_TAG = ForecastLayout.class.getSimpleName();
	
	private Context mContext;
	
	/* 实时天气情况 */
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
	private TextView index;			// 体感温度
	private TextView index_d;		// 穿衣指数
	private TextView index48;		// 48小时体感温度
	private TextView index_d48;		// 48小时穿衣指数
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
		
	}
}
