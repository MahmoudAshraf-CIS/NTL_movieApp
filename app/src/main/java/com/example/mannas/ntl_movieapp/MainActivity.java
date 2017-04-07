package com.example.mannas.ntl_movieapp;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.example.mannas.ntl_movieapp.connection.ConnectionListener;
import com.example.mannas.ntl_movieapp.content.OnlineLoaders.ls_nowplaying;

public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //BroadcastManager.getInstance().RegisterListener(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_activity,new MainFragment()).commit();
    }



}
