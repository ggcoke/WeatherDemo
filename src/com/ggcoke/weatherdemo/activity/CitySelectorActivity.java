package com.ggcoke.weatherdemo.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ggcoke.weatherdemo.R;
import com.ggcoke.weatherdemo.adapter.CityListAdapter;
import com.ggcoke.weatherdemo.db.DBHelper;
import com.ggcoke.weatherdemo.util.MySharedPreferencesEdit;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghuisong on 2014/4/21.
 */
public class CitySelectorActivity extends Activity {
    private static final String LOG_TAG = CitySelectorActivity.class.getSimpleName();

    private RelativeLayout rlHotCity;
    private RelativeLayout rlFilterResult;
    private TextView cityFilter;
    private GridView hotCities;
    private ListView filterCities;
    private CityListAdapter filterAdapter;
    private TextView filterResultNone;
    private List<String> cityData = new ArrayList<String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_selector);
        ArrayAdapter<String> hotCityAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.item_gridview,
                R.id.tv_item_hot_city);
        String cityInfo = MySharedPreferencesEdit.getInstance(this).getHotCities();
        String[] cities = cityInfo.split("_");
        for(String city : cities) {
            hotCityAdapter.add(city);
        }

        rlHotCity = (RelativeLayout) findViewById(R.id.rl_hot_city);
        rlFilterResult = (RelativeLayout) findViewById(R.id.rl_filter_result);
        rlHotCity.setVisibility(View.VISIBLE);
        rlFilterResult.setVisibility(View.GONE);

        hotCities = (GridView) findViewById(R.id.gv_hot_city);
        hotCities.setAdapter(hotCityAdapter);

        filterAdapter = new CityListAdapter(CitySelectorActivity.this, cityData);
        filterCities = (ListView) findViewById(R.id.lv_city_filter_result);
        filterCities.setAdapter(filterAdapter);

        filterResultNone = (TextView) findViewById(R.id.tv_city_filter_noresult);

        cityFilter = (TextView) findViewById(R.id.et_city_filter);
        cityFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                CharSequence filterContent = cityFilter.getText();

                filter(filterContent);
            }
        });
    }

    private void filter(CharSequence content) {
        if (null == content || content.length() <= 0) {
            rlHotCity.setVisibility(View.VISIBLE);
            rlFilterResult.setVisibility(View.GONE);
        } else {
            rlHotCity.setVisibility(View.GONE);
            rlFilterResult.setVisibility(View.VISIBLE);
            cityData.clear();
            getFilterResult(content);

            if (cityData.size() <= 0) {
                filterResultNone.setVisibility(View.VISIBLE);
            } else {
                filterResultNone.setVisibility(View.GONE);
            }

            filterAdapter.notifyDataSetChanged();
        }
    }

    private void getFilterResult(CharSequence content) {
        DBHelper helper = new DBHelper(CitySelectorActivity.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT c1.name, c2.name, c3.name FROM city c1, city c2, city c3 WHERE c1.id = c3.province AND c2.id = c3.district AND c3.name LIKE ? AND c3.level = ? ORDER BY c3.province ASC", new String[]{"%" + content + "%", "3"});
        if (null != cursor && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cityData.add(cursor.getString(0) + "_" + cursor.getString(1) + "_" + cursor.getString(2));
                cursor.moveToNext();
            }
        }
        cursor.close();
    }
}
