package com.ggcoke.weatherdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ggcoke.weatherdemo.R;
import com.ggcoke.weatherdemo.adapter.LeftSlideAdapter;
import com.ggcoke.weatherdemo.util.MySharedPreferencesEdit;

public class LeftSlideFragment extends Fragment{
	private static final String LOG_TAG = LeftSlideFragment.class.getSimpleName();
	private LeftSlideAdapter mLeftSlideAdapter;
	private ListView mCityListView;
	private List<String> mCityNameList = new ArrayList<String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.left_slide, container, false);
		
		mCityListView = (ListView) v.findViewById(R.id.citylistView);
		String cityCodes[] = MySharedPreferencesEdit.getInstance(getActivity()).getCityCodes().split("-");
		for (String cname : cityCodes) {
			String[] code2City = cname.split("_");
			if (!mCityNameList.contains(code2City[1])) {
				mCityNameList.add(code2City[1]);
			}
		}
		
		mLeftSlideAdapter = new LeftSlideAdapter(getActivity(), mCityNameList);
		mCityListView.setAdapter(mLeftSlideAdapter);
		
		return v;
	}
}
