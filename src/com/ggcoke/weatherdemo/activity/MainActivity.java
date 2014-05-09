package com.ggcoke.weatherdemo.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ggcoke.weatherdemo.R;
import com.ggcoke.weatherdemo.fragment.LeftSlideFragment;
import com.ggcoke.weatherdemo.fragment.MainFragment;
import com.ggcoke.weatherdemo.util.WeatherConstants;
import com.ggcoke.weatherdemo.util.WeatherSharedPreferencesEdit;

public class MainActivity extends SherlockFragmentActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private RelativeLayout mRelativeLayout;
    private MainFragment mainFragment;
    private LeftSlideFragment mLeftSlideFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mainFragment = new MainFragment();
        if(WeatherSharedPreferencesEdit.getInstance(this).getSelectedCity() != null) {
            FragmentManager fm2 = getSupportFragmentManager();
            FragmentTransaction ft2 = fm2.beginTransaction();
            ft2.replace(R.id.contentFrameLayout, mainFragment);
            ft2.commitAllowingStateLoss();
        } else {
            Intent intent = new Intent(this, CitySelectorActivity.class);
            startActivityForResult(intent, WeatherConstants.INTENT_CODE_ADD_CITY);
        }

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.drawerLayout);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mLeftSlideFragment = new LeftSlideFragment();
        mLeftSlideFragment.setMainFragment(mainFragment);
        FragmentManager fm1 = getSupportFragmentManager();
        FragmentTransaction ft1 = fm1.beginTransaction();
        ft1.replace(R.id.drawerLayout, mLeftSlideFragment);
        ft1.commitAllowingStateLoss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            if (mDrawerLayout.isDrawerOpen(mRelativeLayout)) {
                mDrawerLayout.closeDrawer(mRelativeLayout);
            } else {
                mDrawerLayout.openDrawer(mRelativeLayout);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (WeatherConstants.INTENT_CODE_SUCCESS == resultCode) {
            String selectedCitis = WeatherSharedPreferencesEdit.getInstance(this).getSelectedCity();
            if (null != selectedCitis) {
                String[] cities = selectedCitis.split(WeatherConstants.SPLIT_STRING_INFO);
                if (1 == cities.length) {
                    FragmentManager fm2 = getSupportFragmentManager();
                    FragmentTransaction ft2 = fm2.beginTransaction();
                    ft2.replace(R.id.contentFrameLayout, mainFragment);
                    ft2.commitAllowingStateLoss();
                } else {
                    mainFragment.addCity(cities[cities.length - 1]);
                }
            } else {
                MainActivity.this.finish();
            }

            mLeftSlideFragment.refreshCityList();
        }
    }
}
