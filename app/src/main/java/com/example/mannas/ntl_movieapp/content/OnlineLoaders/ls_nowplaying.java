package com.example.mannas.ntl_movieapp.content.OnlineLoaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.mannas.ntl_movieapp.Utility;
import com.example.mannas.ntl_movieapp.content.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mannas on 4/6/2017.
 */
public class ls_nowplaying extends AsyncTaskLoader<ArrayList<Movie>> {

    final String LOG_TAG = ls_nowplaying.class.getName();
    OkHttpClient client;
    String uri = "";

    public ls_nowplaying(Context context, Integer Page) {
        super(context);
        client = new OkHttpClient();
       uri = "https://api.themoviedb.org/3/movie/now_playing?" +
                "api_key=" +
                Utility.MovieDB_apiKey+
                "&language=en-US" +
                "&page="+Page.toString();
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        String response="";
        try {
            response = Download(uri);
        } catch (IOException e) {
            Log.e(LOG_TAG,"Error Downloading - NowPlaying List of movies.");
            e.printStackTrace();
        }
        Cach(response);
        ArrayList<Movie> result = new ArrayList<>();
        try {
            result = Parse(response);
        } catch (JSONException e) {
            Log.e(LOG_TAG,"Error Parsing - NowPlaying List of movies.");
            e.printStackTrace();
        }
        return result;
    }

    String Download(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    ArrayList<Movie> Parse(String json) throws JSONException {
        ArrayList<Movie> ls = new ArrayList<>();
            JSONObject all = new JSONObject(json);
            Integer CurentPage = (Integer) all.get("page");
            Integer total_pages = (Integer) all.get("total_pages");
            Integer total_results = (Integer) all.get("total_results");

            JSONArray result = all.getJSONArray("results");
            for(int i=0; i<result.length() ;i++){
                Movie m = Movie.ToMovie(result.getJSONObject(i));
                ls.add(m);
            }
        return ls;
    }
    void Cach(String json){
        //// TODO: 4/6/2017 insert into the local DB
        Log.e(LOG_TAG,json);
    }
    @Override
    protected void onStartLoading() {forceLoad();}
    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
    @Override
    protected void onStopLoading() {cancelLoad();}
    @Override
    public void onCanceled(ArrayList<Movie> data) {
        super.onCanceled(data);
    }




}
