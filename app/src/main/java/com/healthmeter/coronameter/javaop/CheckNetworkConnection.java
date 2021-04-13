package com.healthmeter.coronameter.javaop;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetworkConnection {

    public  void checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.getState()== NetworkInfo.State.CONNECTED || mobile.getState()==NetworkInfo.State.CONNECTED)
            StaticVar.isNetworkConnected=true;
        else
            StaticVar.isNetworkConnected=false;
    }
}
