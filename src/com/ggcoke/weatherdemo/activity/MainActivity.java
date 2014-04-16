package com.ggcoke.weatherdemo.activity;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ggcoke.weatherdemo.fragment.LeftSlideFragment;

public class MainActivity extends SherlockFragmentActivity {
	private static final String LOG_TAG = MainActivity.class.getSimpleName();
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private RelativeLayout mRelativeLayout;
	private LeftSlideFragment mLeftSlideFragment;
	
	private boolean isFirstRun;
}
