/**
 *  the bus de=river
 *  Heshm el kholi
 *  01022215499
 *
 *
 *  reactive programming -- observables
 *   RX java
 *   RX Android
 *   retro fit
 *
 */

package com.example.mannas.ntl_movieapp.Activity_Main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mannas.ntl_movieapp.Activity_Detail.DetailActivity;
import com.example.mannas.ntl_movieapp.Activity_Detail.DetailFragment;
import com.example.mannas.ntl_movieapp.PreferencesManager;
import com.example.mannas.ntl_movieapp.R;
import com.example.mannas.ntl_movieapp.Utility;
import com.example.mannas.ntl_movieapp.connection.BroadcastManager;
import com.example.mannas.ntl_movieapp.connection.ConnectionListener;
import com.example.mannas.ntl_movieapp.content.MovieDetail;
import com.example.mannas.ntl_movieapp.content.LoaderManager;

import java.util.ArrayList;

/**
 * Created by Mannas on 4/6/2017.
 */
public class MainFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Object>,
        ConnectionListener ,
        MainRecyclerAdapter.MainRecyclerAdapter_MoreDataListener{
    View offlineSign,loadingIndicator;
    MainRecyclerAdapter mainRecyclerAdapter;
    SwipeRefreshLayout swipeContainer;

    final Integer id_firstPageLoader =1,id_nextPageLoader=2;
    Integer mPage;
    Boolean loadingMainData  ;
    public static Boolean isoffline,tabletView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage =1;
        loadingMainData = false;
        isoffline = isOffline();
        loadFirstPage();

        mainRecyclerAdapter = new MainRecyclerAdapter(this, new MainRecyclerAdapter.MovieItemOnClickListener() {
            @Override
            public void OnItemClick(MovieDetail m) {
                if(tabletView){
                    //Toast.makeText(getContext(),"tablet",Toast.LENGTH_LONG).show();
                    DetailFragment detailFragment = new DetailFragment();
                    Bundle args = new Bundle();
                    args.putParcelable(Utility.movieDetail_key,m);
                    detailFragment.setArguments(args);
                    getChildFragmentManager().beginTransaction()
                            .add(R.id.right_pan, detailFragment ).commit();
                }else{
                    //Toast.makeText(getContext(),"new activity",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getContext(),DetailActivity.class);
                    i.putExtra(Utility.movieDetail_key,m);
                    startActivity(i);
                }
            }
        });



        BroadcastManager.getInstance().RegisterListener(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        BroadcastManager.getInstance().UnRegisterListener(this);
    }

    public ArrayList<MovieDetail> getInstanceState(){
        return mainRecyclerAdapter.getDataSet();
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


        swipeContainer = (SwipeRefreshLayout) root.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFirstPage();
                swipeContainer.setRefreshing(false);
            }
        });
        loadingIndicator = root.findViewById(R.id.main_loding);
        setLoadingIndicator();
        tabletView = root.findViewById(R.id.right_pan)!=null ;

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MaterialDialog.SingleButtonCallback onCancel =new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

            }
        };

        if(item.getItemId()== R.id.orderByAction){
            String[] arr = getResources().getStringArray(R.array.orderByList);
            final Integer sellectedIndex = PreferencesManager.getOrderBy(getContext());
            new MaterialDialog.Builder(getContext())
                    .title(R.string.orderBy)
                    .items(R.array.orderByList)
                    .itemsCallbackSingleChoice(sellectedIndex , new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            /**
                             * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                             * returning false here won't allow the newly selected radio button to actually be selected.
                             **/
                            dialog.setSelectedIndex(which);
                            PreferencesManager.putOrderBy(getContext(),which,text.toString());
                            loadFirstPage();
                            dialog.dismiss();
                            return true;
                        }
                    })
                    .positiveText("Cancel")
                    .onPositive(onCancel)
                    .alwaysCallSingleChoiceCallback()
                    .show();
        }
        return true;
    }

    /******************* LoaderCallbacks *********************/
    @Override
    public void OnRequestMoreDate() {
        loadNextPage();
    }
    public void loadFirstPage(){
            getLoaderManager().restartLoader(id_firstPageLoader,null,this).forceLoad();
    }
    public void loadNextPage(){
            getLoaderManager().restartLoader(id_nextPageLoader,null,this).forceLoad();
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args){
        loadingMainData = true;
        setLoadingIndicator();

        if(id == id_firstPageLoader){
            mPage = 1;
            if(PreferencesManager.getOrderBy(getContext()) == 4){ // the index of the My Favourits in the settings
                return LoaderManager.getFavLoader(getContext());
            }
            if(mainRecyclerAdapter !=null)
                mainRecyclerAdapter.changeDataSet(new ArrayList<MovieDetail>());
            return LoaderManager.getListLoader(PreferencesManager.getOrderBy(getContext()),getContext(),mPage, isoffline);
        }
        else if(id == id_nextPageLoader &&PreferencesManager.getOrderBy(getContext()) != 4 ){
            mPage++;
            return LoaderManager.getListLoader(PreferencesManager.getOrderBy(getContext()),getContext(),mPage, isoffline);
        }
        return null ;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if(loader.getId() == id_firstPageLoader){
            if ( data!=null && data instanceof ArrayList) {
                ArrayList<MovieDetail> ls = new ArrayList<>();
                for (Object o : ((ArrayList) data)) {
                    if(o instanceof MovieDetail){
                        ls.add(((MovieDetail) o));
                    }
                }
                if(mainRecyclerAdapter!=null)
                    mainRecyclerAdapter.changeDataSet(ls);
            }
        }
        else if(loader.getId() == id_nextPageLoader){
            if (data!=null && data instanceof ArrayList) {
                ArrayList<MovieDetail> ls = new ArrayList<>();
                for (Object o : ((ArrayList) data)) {
                    if(o instanceof MovieDetail){
                        ls.add(((MovieDetail) o));
                    }
                }
                if(mainRecyclerAdapter!=null)
                    mainRecyclerAdapter.addToDataSet(ls);
            }
        }
        loadingMainData = false;
        setLoadingIndicator();
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    /*************** Act on the Connection state Change **************/
    @Override
    public void OnConnectionStateChanged(Boolean isOffline) {
        isoffline = isOffline;
        setOfflineSign();
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
        if(isoffline){
            offlineSign.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(),"Check your Internet Connection",Toast.LENGTH_LONG).show();
        }else{
            offlineSign.setVisibility(View.GONE);
        }
    }
    void setLoadingIndicator(){
        if(loadingIndicator!=null){
            if(loadingMainData){
                loadingIndicator.setVisibility(View.VISIBLE);
            }else{
                loadingIndicator.setVisibility(View.GONE);
            }
        }
    }

    public void refresh(){
        if(PreferencesManager.getOrderBy(getContext()) == 4)
            loadFirstPage();
    }



}