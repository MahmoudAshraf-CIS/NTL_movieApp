package com.example.mannas.ntl_movieapp.Activity_Detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.mannas.ntl_movieapp.R;

/**
 * Created by Mannas on 4/10/2017.
 */
public class DetailActivity extends AppCompatActivity implements DetailFragment.OnFragmentInteractionListener {

    DetailFragment detailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        detailFragment = new DetailFragment();
        Bundle ex = getIntent().getExtras();
        detailFragment.setArguments(ex);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_activity, detailFragment).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

