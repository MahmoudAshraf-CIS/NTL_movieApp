package com.example.mannas.ntl_movieapp;

/**
 * Created by Mannas on 4/7/2017.
 */
public class PreferencesManager {
    PreferencesManager mManager;
    private PreferencesManager(){

    }

    public PreferencesManager getInstence(){
        if(mManager==null){
            mManager = new PreferencesManager();
        }
        return mManager;
    }
}
