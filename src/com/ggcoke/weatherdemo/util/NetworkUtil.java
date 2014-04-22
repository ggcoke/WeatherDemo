package com.ggcoke.weatherdemo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    public static boolean netWorkAvilable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos != null) {
                for (int i = 0, count = networkInfos.length; i < count; i++) {
                    if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
