package com.example.mannas.ntl_movieapp.Activity_Main;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.mannas.ntl_movieapp.Activity_Detail.DetailFragment;
import com.example.mannas.ntl_movieapp.R;
import com.example.mannas.ntl_movieapp.Utility;

public class MainActivity extends AppCompatActivity implements DetailFragment.OnFragmentInteractionListener {


    MainFragment mainFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //BroadcastManager.getInstance().RegisterListener(this);
        mainFragment = new MainFragment();
        mainFragment.setArguments(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_activity,mainFragment,MainFragment.class.getName()).commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Utility.MainRecyclerList_Key,mainFragment.getInstanceState());
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
