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
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
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
}
