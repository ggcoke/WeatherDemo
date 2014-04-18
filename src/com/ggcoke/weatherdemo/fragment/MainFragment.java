package com.ggcoke.weatherdemo.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ggcoke.weatherdemo.R;
import com.ggcoke.weatherdemo.adapter.ViewPagerAdapter;
import com.ggcoke.weatherdemo.util.MySharedPreferencesEdit;
import com.ggcoke.weatherdemo.view.ForecastLayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainFragment extends Fragment {
	private ViewPager viewPager;
	private ArrayList<ForecastLayout> listViews = new ArrayList<ForecastLayout>();
	private LinearLayout llImage;
	private ViewPagerAdapter adapter;
	private List<String> cityList;
	
	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int position) {
			ForecastLayout view = listViews.get(position);
			String cityInfo = cityList.get(position);
            view.initView(cityInfo);
            changeIcon(position);
            setCityWeather(view, cityInfo);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			return;
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_fragment, group, false);
		
		return view;
	}
	
	private void initView(View v) {
		llImage = (LinearLayout) v.findViewById(R.id.ll_img_content);
		viewPager = (ViewPager) v.findViewById(R.id.viewpager_forecast_content);
		getCities();
		
		for (int i = 0; i < cityList.size(); i++) {
			ForecastLayout view = (ForecastLayout) View.inflate(getActivity(), R.layout.forecast, null);
			listViews.add(view);
		}
		
		adapter = new ViewPagerAdapter(listViews);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(onPageChangeListener);
		
	}
	
	private void getCities() {
		String[] cityInfos = MySharedPreferencesEdit.getInstance(getActivity()).getCityCodes().split("-");
        cityList = Arrays.asList(cityInfos);
	}

    private void setCityWeather(ForecastLayout view, String cityInfo) {
        String cityCode = cityInfo.split("_")[1];
        view.setWeatherInfo(cityCode);
    }

    private void changeIcon(int position) {
        llImage.removeAllViewsInLayout();
        for(int i =0;i<listViews.size();i++) {
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 0, 0, 0);
            imageView.setLayoutParams(layoutParams);
            if(i==position) {
                imageView.setImageResource(R.drawable.img_du_pressed);
            } else {
                imageView.setImageResource(R.drawable.img_du_normal);
            }
            llImage.addView(imageView);
        }
    }
}
