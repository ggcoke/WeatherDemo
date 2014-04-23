package com.ggcoke.weatherdemo.adapter;

import java.util.List;

import com.ggcoke.weatherdemo.R;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LeftSlideAdapter extends BaseAdapter {
    private static final String LOG_TAG = LeftSlideAdapter.class.getSimpleName();
    private Context context;
    private List<String> list;
    
    public LeftSlideAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }
    
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;
        if (convertView == null) {
            textView = getGenericView();
        } else {
            textView = (TextView) convertView;
        }
        textView.setText(list.get(position));

        return textView;
    }

    private TextView getGenericView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80);
        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setTextSize(R.dimen.text_size_large);
        textView.setTextColor(context.getResources().getColor(R.color.left_listview_tv));
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(R.dimen.padding_left_slide_city_item, R.dimen.margin_none, R.dimen.margin_none,R.dimen.margin_none);
        return textView;
    }

    public void setCityList(List<String> nameList) {
        this.list = nameList;
    }
}
