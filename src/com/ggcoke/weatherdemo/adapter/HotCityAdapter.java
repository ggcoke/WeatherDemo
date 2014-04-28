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
 * Created by ggcoke on 14-4-24.
 */
public class HotCityAdapter extends BaseAdapter {
    private static final String LOG_TAG = HotCityAdapter.class.getSimpleName();

    private Context mContext;
    private List<String> data;
    private LayoutInflater inflater;

    public HotCityAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        String[] infos = data.get(position).split("_");

        HotCityViewHolder holder = null;
        if (null == view) {
            view = inflater.inflate(R.layout.item_gridview, null);
            holder = new HotCityViewHolder();
            holder.tvHotCityName = (TextView) view.findViewById(R.id.tv_item_hot_city);
            view.setTag(holder);
        } else {
            holder = (HotCityViewHolder) view.getTag();
        }

        holder.tvHotCityName.setText(infos[infos.length - 1]);

        return view;
    }

    private static class HotCityViewHolder {
        public TextView tvHotCityName;
    }
}
