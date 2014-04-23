package com.ggcoke.weatherdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ggcoke.weatherdemo.R;
import com.ggcoke.weatherdemo.util.EditableCity;
import com.ggcoke.weatherdemo.util.WeatherSharedPreferencesEdit;

import java.util.List;

/**
 * Created by wanghuisong on 2014/4/23.
 */
public class ListSwipAdapter extends BaseAdapter {
    private static final String LOG_TAG = ListSwipAdapter.class.getSimpleName();
    private Context mContext;
    private List<EditableCity> mData;

    public ListSwipAdapter(Context context, List<EditableCity> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (null == view) {
            view = LayoutInflater.from(mContext).inflate(R.layout.left_city_item, null);
            holder = new ViewHolder();
            holder.tvCityItem = (TextView) view.findViewById(R.id.tv_left_slide_city_item);
            holder.btnRemove = (ImageButton) view.findViewById(R.id.btn_left_slide_city_remove);
            holder.btnRemove.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    mData.remove(position);
                    StringBuilder sb = new StringBuilder();
                    for (EditableCity data : mData) {
                        sb.append(data.getCityName()).append("-");
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                        WeatherSharedPreferencesEdit.getInstance(mContext).storeSelectedCity(sb.toString());
                    } else {
                        WeatherSharedPreferencesEdit.getInstance(mContext).storeSelectedCity(null);
                    }
                    notifyDataSetChanged();
                }
            });

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String[] cityInfo = mData.get(position).getCityName().split("_");
        holder.tvCityItem.setText(cityInfo[cityInfo.length - 1]);
        if (mData.get(position).isEditing()) {
            holder.btnRemove.setVisibility(View.VISIBLE);
        } else {
            holder.btnRemove.setVisibility(View.GONE);
        }

        return view;
    }


    public class ViewHolder {
        public TextView tvCityItem;
        public ImageButton btnRemove;
    }


}
