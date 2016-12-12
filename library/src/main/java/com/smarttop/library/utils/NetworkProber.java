package com.smarttop.library.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

/**
 * Created by smartTop on 2015/7/7.
 * 判断手机当前的网络情况的工具类
 *
 */
public class NetworkProber {
    private static NetworkProber netWorkState;
    private onNetWorkStateListener netStateChange;
    private List<onNetWorkStateListener> mNetListeners = null;
    public static NetworkProber getInstance() {
        if (null == netWorkState)
            netWorkState = new NetworkProber();

        return netWorkState;
    }

    public void registReceiver(Context context) {
        IntentFilter filter = new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(myNetReceiver, filter);
    }

    public void unregistReceiver(Context context) {
        if (myNetReceiver != null) {
            context.unregisterReceiver(myNetReceiver);
        }
    }


    private ConnectivityManager mConnectivityManager;

    private NetworkInfo netInfo;

/////////////监听网络状态变化的广播接收器

    private BroadcastReceiver myNetReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

                mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                netInfo = mConnectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {
                    if (netStateChange != null)
                        netStateChange.networkChange(true);
                } else {
                    if (netStateChange != null)
                        netStateChange.networkChange(false);
                }
            }

        }
    };
    public  boolean isNetworkAvailable(Context context){
        mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = mConnectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isAvailable()) {
            return true;
        }else{
            return false;
        }
    }
    public void setNetWorkStateListener(onNetWorkStateListener netStateChange) {
        this.netStateChange = netStateChange;
    }

    public interface onNetWorkStateListener {
        void networkChange(boolean flag);
    }
}
