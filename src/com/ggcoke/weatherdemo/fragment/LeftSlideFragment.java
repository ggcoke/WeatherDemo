package com.ggcoke.weatherdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ggcoke.weatherdemo.R;
import com.ggcoke.weatherdemo.activity.CitySelectorActivity;
import com.ggcoke.weatherdemo.adapter.ListSwipAdapter;
import com.ggcoke.weatherdemo.util.EditableCity;
import com.ggcoke.weatherdemo.util.WeatherConstants;
import com.ggcoke.weatherdemo.util.WeatherSharedPreferencesEdit;

import java.util.ArrayList;
import java.util.List;

public class LeftSlideFragment extends Fragment {
    private static final String LOG_TAG = LeftSlideFragment.class.getSimpleName();

    private ListSwipAdapter mListSwipAdapter;
    private ListView mCityListView;
    private List<EditableCity> mCityList = new ArrayList<EditableCity>();
    private ImageButton mBtnCitySetting;
    private ImageButton mBtnCityAdd;
    private boolean editing = false;
    private MainFragment mainFragment;

    private View.OnClickListener mCitySettingListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            editing = !editing;
            if (editing) {
                mBtnCitySetting.setBackground(getResources().getDrawable(R.drawable.setting_complete));
            } else {
                mBtnCitySetting.setBackground(getResources().getDrawable(R.drawable.setting_edit));
            }
            for (EditableCity editableCity : mCityList) {
                editableCity.setEditing(editing);
            }

            mListSwipAdapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener mCityAddListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), CitySelectorActivity.class);
            getActivity().startActivityForResult(intent, WeatherConstants.INTENT_CODE_ADD_CITY);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.left_slide, container, false);

        mCityListView = (ListView) v.findViewById(R.id.citylistView);
        String cities = WeatherSharedPreferencesEdit.getInstance(getActivity()).getSelectedCity();
        if (null != cities) {
            String cityCodes[] = cities.split("-");
            for (String cname : cityCodes) {
                EditableCity city = new EditableCity(cname, false);
                if (!mCityList.contains(city)) {
                    mCityList.add(city);
                }
            }
        }

        mBtnCityAdd = (ImageButton) v.findViewById(R.id.btn_left_slide_city_add);
        mBtnCityAdd.setOnClickListener(mCityAddListener);

        mBtnCitySetting = (ImageButton) v.findViewById(R.id.btn_left_slide_city_setting);
        mBtnCitySetting.setOnClickListener(mCitySettingListener);

        mListSwipAdapter = new ListSwipAdapter(getActivity(), mCityList, mainFragment);
        mCityListView.setAdapter(mListSwipAdapter);

        return v;
    }

    public void refreshCityList() {
        String[] cities = WeatherSharedPreferencesEdit.getInstance(getActivity()).getSelectedCity().split("-");
        EditableCity city = new EditableCity(cities[cities.length - 1], false);
        mCityList.add(city);
        mListSwipAdapter.notifyDataSetChanged();
    }

    public void setMainFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }
}
