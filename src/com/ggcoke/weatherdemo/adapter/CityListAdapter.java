package com.ggcoke.weatherdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ggcoke.weatherdemo.R;

import java.util.List;

/**
 * Created by wanghuisong on 2014/4/21.
 */
public class CityListAdapter extends BaseAdapter {
    private static final String LOG_TAG = CityListAdapter.class.getSimpleName();

    private Context context;
    private List<String> cities;
    private LayoutInflater inflater;

    public CityListAdapter(Context context, List<String> cities) {
        this.context = context;
        this.cities = cities;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<String> cities) {
        this.cities = cities;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        String[] infos = cities.get(position).split("_");

        CityItemViewHolder holder = null;
        if (null == view) {
            view = inflater.inflate(R.layout.city_item, null);
            holder = new CityItemViewHolder();
            holder.tvCity = (TextView) view.findViewById(R.id.tv_city_item_name);
            view.setTag(holder);
        } else {
            holder = (CityItemViewHolder) view.getTag();
        }

        holder.tvCity.setText(infos[2] + " - " + infos[0]);

        return view;
    }

    private static class CityItemViewHolder {
        public TextView tvCity;
    }
}
