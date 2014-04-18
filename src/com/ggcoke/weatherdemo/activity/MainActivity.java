package com.ggcoke.weatherdemo.activity;

import android.content.Intent;
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
import com.ggcoke.weatherdemo.R;
import com.ggcoke.weatherdemo.fragment.LeftSlideFragment;
import com.ggcoke.weatherdemo.fragment.MainFragment;
import com.ggcoke.weatherdemo.util.MySharedPreferencesEdit;

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
        if(MySharedPreferencesEdit.getInstance(this).getCityCodes() != null) {
            FragmentManager fm2 = getSupportFragmentManager();
            FragmentTransaction ft2 = fm2.beginTransaction();
            ft2.replace(R.id.contentFrameLayout, mainFragment);
            ft2.commitAllowingStateLoss();
        } else {
            // 跳转到设置城市的Activity
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
        FragmentManager fm1 = getSupportFragmentManager();
        FragmentTransaction ft1 = fm1.beginTransaction();
        ft1.replace(R.id.drawerLayout, mLeftSlideFragment);
        ft1.commitAllowingStateLoss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
