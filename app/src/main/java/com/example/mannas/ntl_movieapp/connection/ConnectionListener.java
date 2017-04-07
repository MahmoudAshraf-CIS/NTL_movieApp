package com.example.mannas.ntl_movieapp.connection;

import android.content.Intent;
import android.net.NetworkInfo;

/**
 * Created by Mannas on 4/5/2017.
 */

public interface ConnectionListener {

    /**
     * Note :- it can be called multi times in a row
     *          it's system error
     *          SO handle each unnecessary calls
     *
     * @param intent  the intent passed from the system to {@link ConnectionBroadcastReceiver}.OnRecive()
     * @param networkInfo  contain the network info, {@value = null }if there is no network
     */
    void OnConnectionStateChanged(Intent intent, NetworkInfo networkInfo);

}
