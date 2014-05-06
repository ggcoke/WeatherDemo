package com.ggcoke.weatherdemo.activity;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ggcoke.weatherdemo.R;
import com.ggcoke.weatherdemo.adapter.CityListAdapter;
import com.ggcoke.weatherdemo.adapter.HotCityAdapter;
import com.ggcoke.weatherdemo.provider.CityMetaData;
import com.ggcoke.weatherdemo.util.WeatherConstants;
import com.ggcoke.weatherdemo.util.WeatherSharedPreferencesEdit;

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
    private HotCityAdapter hotCityAdapter;
    private List<String> hotCityData = new ArrayList<String>();

    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private LocationListener locationListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_selector);

        String cityInfo = WeatherSharedPreferencesEdit.getInstance(this).getHotCities();
        String[] cities = cityInfo.split("-");
        String selectedCities = WeatherSharedPreferencesEdit.getInstance(this).getSelectedCity();
        hotCityData.add(0, getResources().getString(R.string.city_auto_location_hint));
        for (String city : cities) {
            if (hotCityData.contains(city) || (null != selectedCities && selectedCities.contains(city))) {
            } else {
                hotCityData.add(city);
            }
        }

        hotCityAdapter = new HotCityAdapter(CitySelectorActivity.this, hotCityData);
        hotCities = (GridView) findViewById(R.id.gv_hot_city);
        hotCities.setAdapter(hotCityAdapter);

        rlHotCity = (RelativeLayout) findViewById(R.id.rl_hot_city);
        rlFilterResult = (RelativeLayout) findViewById(R.id.rl_filter_result);
        rlHotCity.setVisibility(View.VISIBLE);
        rlFilterResult.setVisibility(View.GONE);

        filterAdapter = new CityListAdapter(CitySelectorActivity.this, cityData);
        filterCities = (ListView) findViewById(R.id.lv_city_filter_result);
        filterCities.setAdapter(filterAdapter);

        filterResultNone = (TextView) findViewById(R.id.tv_city_filter_noresult);

        locationListener = new LocationListener();
        locationClient = new LocationClient(getApplicationContext());
        locationClient.setAK(WeatherConstants.BD_LOCATION_KEY);
        locationClient.registerLocationListener(locationListener);
        location();

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

        hotCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String currentCityList = WeatherSharedPreferencesEdit.getInstance(CitySelectorActivity.this).getSelectedCity();
                if (null == currentCityList || currentCityList.length() == 0) {
                    currentCityList = hotCityData.get(position);
                } else {
                    currentCityList += ("-" + hotCityData.get(position));
                }

                WeatherSharedPreferencesEdit.getInstance(CitySelectorActivity.this).storeSelectedCity(currentCityList);
                setResult(WeatherConstants.INTENT_CODE_SUCCESS);
                finish();
            }
        });

        filterCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String currentCityList = WeatherSharedPreferencesEdit.getInstance(CitySelectorActivity.this).getSelectedCity();
                if (null == currentCityList || currentCityList.length() == 0) {
                    currentCityList = cityData.get(position);
                } else {
                    currentCityList += ("-" + cityData.get(position));
                }

                WeatherSharedPreferencesEdit.getInstance(CitySelectorActivity.this).storeSelectedCity(currentCityList);
                setResult(WeatherConstants.INTENT_CODE_SUCCESS);
                finish();
            }
        });
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WeatherConstants.AUTO_LOCATION_FAILED:
                    hotCityData.set(0, getResources().getString(R.string.city_auto_location_error));
                    hotCityAdapter.notifyDataSetChanged();
                    break;
                case WeatherConstants.AUTO_LOCATION_SUCCESS:
                    hotCityData.set(0, msg.getData().getString("location"));
                    hotCityAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    private void setLocationOptions() {
        locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.disableCache(true);
        locationClientOption.setAddrType("all");
        locationClientOption.setPriority(LocationClientOption.GpsFirst);
        locationClient.setLocOption(locationClientOption);
    }

    private void location() {
        setLocationOptions();
        if (!locationClient.isStarted()) {
            locationClient.start();
        }

        locationClient.requestLocation();
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
        Uri uri = Uri.withAppendedPath(CityMetaData.City.CONTENT_NAME_URI_BASE, content.toString());
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (null != cursor && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cityData.add(cursor.getString(0) + "_" + cursor.getString(1) + "_" + cursor.getString(2));
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    private class LocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || bdLocation.getDistrict() == null || bdLocation.getDistrict().equals("")) {
                hotCityData.set(0, getResources().getString(R.string.city_auto_location_error));
                hotCityAdapter.notifyDataSetChanged();
                return;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(bdLocation.getProvince()).append("_")
                        .append(bdLocation.getCity()).append("_")
                        .append(bdLocation.getDistrict());
                hotCityData.set(0, sb.toString());
                hotCityAdapter.notifyDataSetChanged();
                locationClient.stop();
            }

        }

        @Override
        public void onReceivePoi(BDLocation bdLocation) {
        }
    }
}
