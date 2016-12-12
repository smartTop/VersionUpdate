package com.smarttop.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by smartTop on 2015/7/7.
 */
public class NetworkProber {
    private static NetworkProber netWorkState;

    public static NetworkProber getInstance() {
        if (null == netWorkState)
            netWorkState = new NetworkProber();

        return netWorkState;
    }


    private ConnectivityManager mConnectivityManager;

    private NetworkInfo netInfo;


    public boolean isNetworkAvailable(Context context) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = mConnectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }
}
