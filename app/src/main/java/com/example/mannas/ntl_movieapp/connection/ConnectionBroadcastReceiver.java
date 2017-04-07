package com.example.mannas.ntl_movieapp.connection;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by Mannas on 4/5/2017.
 */
public class ConnectionBroadcastReceiver extends android.content.BroadcastReceiver {

    public ConnectionBroadcastReceiver(){

    }

    /**
     * Note :- this method is called multi times by the system
     *      after stackoverflow it ,turns that it's the system fault :)
     *      So => Handle each unnecessary change in each {@link ConnectionListener}
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        BroadcastManager.getInstance().Dispatch( context , intent);
    }
}
