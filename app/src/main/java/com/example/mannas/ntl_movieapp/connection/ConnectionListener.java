package com.example.mannas.ntl_movieapp.connection;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mannas on 4/5/2017.
 */

public interface ConnectionListener {

    /**
     * Note :- it can be called multi times in a row
     *          it's system error
     *          SO handle each unnecessary calls
     */
    void OnConnectionStateChanged(Boolean isOffline);

}
