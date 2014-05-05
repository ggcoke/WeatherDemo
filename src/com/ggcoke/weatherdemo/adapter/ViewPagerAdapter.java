package com.ggcoke.weatherdemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ggcoke.weatherdemo.view.ForecastLayout;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private ArrayList<ForecastLayout> listViews;

    public ViewPagerAdapter(ArrayList<ForecastLayout> listViews) {
        this.listViews = listViews;
    }

    public void setList(ArrayList<ForecastLayout> listViews) {
        this.listViews = listViews;
    }

    @Override
    public int getCount() {
        return listViews.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(listViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(listViews.get(position));
        return listViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
