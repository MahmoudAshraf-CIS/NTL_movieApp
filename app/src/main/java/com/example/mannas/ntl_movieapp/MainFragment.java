package com.example.mannas.ntl_movieapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mannas.ntl_movieapp.connection.BroadcastManager;
import com.example.mannas.ntl_movieapp.connection.ConnectionListener;
import com.example.mannas.ntl_movieapp.content.Movie;
import com.example.mannas.ntl_movieapp.content.OnlineLoaders.ls_nowplaying;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mannas on 4/6/2017.
 */
public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Object>,ConnectionListener {
    View offlineSign;
    MainRecyclerAdapter mainRecyclerAdapter;
    SwipeRefreshLayout swipeContainer;
    Integer mPage;
    Boolean offlineMode = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage=1;
        getLoaderManager().
                initLoader(1,null,this);
        mainRecyclerAdapter = new MainRecyclerAdapter();
        offlineMode = isOffline();

        BroadcastManager.getInstance().RegisterListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_main,container,false);

        offlineSign = root.findViewById(R.id.offline_sign);
        setOfflineSign();

        final RecyclerView mainRecycler = (RecyclerView) root.findViewById(R.id.main_recycler);
        mainRecycler.setAdapter(mainRecyclerAdapter);
        mainRecycler.setLayoutManager(new GridLayoutManager(getContext(),2,1,false));

        //infinite scrolling items
        mainRecycler.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(!recyclerView.canScrollVertically(1)){
                    //// TODO: 4/7/2017 load the next page - append it's data
                    //reached the end of the recycler
                }
            }
        });

        swipeContainer = (SwipeRefreshLayout) root.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //// TODO: 4/7/2017 refresh the recycler from page 1

                swipeContainer.setRefreshing(false);
            }
        });


        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
    }

    /******************* LoaderCallbacks *********************/
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if(id==1){
            ls_nowplaying loader = new ls_nowplaying(getContext(),mPage);
            return loader;
        }
        return null ;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if(loader.getId()==1){
            if (data instanceof ArrayList) {
                ArrayList<Movie> ls = new ArrayList<>();
                for (Object o : ((ArrayList) data)) {
                    if(o instanceof Movie){
                        ls.add(((Movie) o));
                    }
                }
                mPage++;
                mainRecyclerAdapter.changeDataSet(ls);
                Log.i("ggggggg","ffff"+ls.size());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    /*************** Act on the Connection state Change **************/
    @Override
    public void OnConnectionStateChanged(Intent intent, NetworkInfo networkInfo) {
        if(intent!=null && networkInfo !=null){
            if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
                if(networkInfo.isConnected()){
                    offlineMode = false;
                }else{
                    offlineMode = true;
                }
                setOfflineSign();
            }
        }
    }

    Boolean isOffline(){
        ConnectivityManager connManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if(info==null){
            return true;
        }else {
            return !info.isConnected();
        }
    }
    void setOfflineSign(){
        if(offlineMode){
            offlineSign.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(),"Check your Internet Connection",Toast.LENGTH_LONG).show();
        }else{
            offlineSign.setVisibility(View.GONE);
        }
    }

}