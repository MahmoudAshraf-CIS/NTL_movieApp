package com.example.mannas.ntl_movieapp.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.mannas.ntl_movieapp.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mannas on 4/8/2017.
 */
public class LoaderManager {

    public static final Integer IDls_nowPlaying =0;
    public static final Integer IDls_popular =1;
    public static final Integer IDls_TopRated =2;
    public static final Integer IDls_upcoming =3;

    public static final Integer IDMovieFullDetail = 4;
    public static final Integer IDMovieReviews = 5;
    public static final Integer IDMoviewVideos = 6;
    public static final Integer IDMoviewFav = 7;


    public static onlineLoader getListLoader(Integer OrderBy, Context context, Integer page,Boolean isOffline){
        if(OrderBy == IDls_nowPlaying)
            return new ls_nowplayingLoader(context,page,isOffline);
        else if(OrderBy==IDls_popular)
            return new ls_popularLoader(context,page,isOffline);
        else if(OrderBy == IDls_TopRated)
            return new ls_TopRatedLoader(context,page,isOffline);
        else if(OrderBy==IDls_upcoming)
            return new ls_upcomingLoader(context,page,isOffline);
        else
            return null;
    }


    public static MovieFullDetailsLoader getMovieFullDetailsLoader(Context context, Integer MovieID,Boolean isOffline){
        return new MovieFullDetailsLoader(context,MovieID,isOffline);
    }
    public static MovieVideosLoader getMovieVideosLoader(Context context, Integer MovieID,Boolean isOffline){
        return new MovieVideosLoader(context,MovieID,isOffline);
    }
    public static MovieReviewsLoader getMovieReviewsLoader(Context context, Integer MovieID, Integer Page,Boolean isOffline){
        return new MovieReviewsLoader(context,MovieID,Page,isOffline);
    }
    public static ls_FavLoader getFavLoader(Context context){
        return new ls_FavLoader(context);
    }










    static class ls_FavLoader extends AsyncTaskLoader<ArrayList<MovieDetail>> {
        ArrayList<MovieDetail> ls;
        public ls_FavLoader(Context context) {
            super(context);
            ls = new ArrayList<>();
        }

        @Override
        public ArrayList<MovieDetail> loadInBackground() {
            Cursor c = getContext().getContentResolver().query(Contract.movie_Fav.uri,null,null,null,null);
            if(c!=null){
                if (c.moveToFirst()){
                    do{
                        String json = c.getString( c.getColumnIndex( Contract.movie_Fav.Columns.json));
                        MovieDetail m=null;
                        try {
                            m= MovieDetail.fromJSON(new JSONObject(json));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(m != null)
                            ls.add(m);
                    }while (c.moveToNext());
                }
                c.close();
            }

            return ls;

        }
    }



    /**
     * Created by Mannas on 4/8/2017.
     */
    static class onlineLoader extends AsyncTaskLoader<ArrayList<MovieDetail>> {

        String LOG_TAG ="",ERR_DOWNLOADING ="" ,ERR_PARSING ="",uri = "";
        OkHttpClient client;
        Integer mPage;
        Uri LOCAL_DB_URI;
        Boolean isOffline;

        /**
         * DO NOT Instantiate any instance of this loader
         * call {@link LoaderManager}.getListLoader()
         * @param context
         * @param Page
         */
        public onlineLoader(Context context, Integer Page,Boolean isOffline) {
            super(context);
            this.isOffline = isOffline;

            LOG_TAG = onlineLoader.class.getName();
            ERR_DOWNLOADING="";
            ERR_PARSING = "";
            uri = "";
            client = new OkHttpClient();
            mPage = Page;
        }

        @Override
        public ArrayList<MovieDetail> loadInBackground() {
            String response="";
            try {
                response = Download(this.uri);
            } catch (IOException e) {
                Log.e(LOG_TAG,ERR_DOWNLOADING);
                e.printStackTrace();
            }
            if(!isOffline)
                Cache(response);

            ArrayList<MovieDetail> result = new ArrayList<>();
            try {
                result = Parse(response);
            } catch (JSONException e) {
                Log.e(LOG_TAG,ERR_PARSING);
                e.printStackTrace();
                result = null;
            }
            return result;
        }

        String Download(String url) throws IOException {
            if(!isOffline){
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }
            else {
                String []projection = {Contract.ls_nowplaying.Columns.json};
                //String []selectionArgs = {};
                Cursor c = getContext().getContentResolver().query(LOCAL_DB_URI,projection, Contract.ls_nowplaying.Columns.page+" = "+mPage,null,null);
                if( c!=null && c.moveToFirst()){
                    String json = c.getString(c.getColumnIndex(projection[0]));
                    c.close();
                    return json;
                }
            }
            return "";
        }

        ArrayList<MovieDetail> Parse(String json) throws JSONException {
            ArrayList<MovieDetail> ls = new ArrayList<>();
            JSONObject all = new JSONObject(json);
            Integer CurentPage = (Integer) all.get("page");
            Integer total_pages = (Integer) all.get("total_pages");
            Integer total_results = (Integer) all.get("total_results");

            JSONArray result = all.getJSONArray("results");
            for(int i=0; i<result.length() ;i++){
                MovieDetail m = MovieDetail.fromJSON(result.getJSONObject(i));
                ls.add(m);
            }
            return ls;
        }
        void Cache(final String json){

            new AsyncTask<Void, Void, Void>() {
                protected void onPreExecute() {}
                protected Void doInBackground(Void... unused) {
                    if( json!=null && !json.equals("") ){
                        ContentValues v = new ContentValues(1);
                        switch (Contract.mUriMatcher.match(LOCAL_DB_URI)) {
                            case Contract.TableIdentifier.ls_nowplaying:
                                v.put(Contract.ls_nowplaying.Columns.json,json );
                                v.put(Contract.ls_nowplaying.Columns.page,mPage );
                                getContext().getContentResolver().insert(LOCAL_DB_URI, v);
                                break;
                            case Contract.TableIdentifier.ls_popular:
                                v.put(Contract.ls_popular.Columns.json,json );
                                v.put(Contract.ls_popular.Columns.page,mPage );
                                getContext().getContentResolver().insert(LOCAL_DB_URI, v);
                                break;
                            case Contract.TableIdentifier.ls_toprated:
                                v.put(Contract.ls_toprated.Columns.json,json );
                                v.put(Contract.ls_toprated.Columns.page,mPage );
                                getContext().getContentResolver().insert(LOCAL_DB_URI, v);
                                break;
                            case Contract.TableIdentifier.ls_upcoming:
                                v.put(Contract.ls_upcoming.Columns.json,json );
                                v.put(Contract.ls_upcoming.Columns.page,mPage );
                                getContext().getContentResolver().insert(LOCAL_DB_URI, v);
                                break;
                        }
                    }
                    return null;
                }
                protected void onPostExecute(Void unused) {}
            }.execute();
            Log.e(LOG_TAG,"Cached !");
        }
    }

    static class ls_nowplayingLoader extends onlineLoader {

        public ls_nowplayingLoader(Context context, Integer Page,Boolean isOffline) {
            super(context,Page,isOffline);
            LOG_TAG = ls_nowplayingLoader.class.getName();
            ERR_DOWNLOADING = "Error Downloading - NowPlaying List of movies.";
            ERR_PARSING = "Error Parsing - NowPlaying List of movies.";
            uri = "https://api.themoviedb.org/3/movie/now_playing?" +
                    "api_key=" +
                    Utility.MovieDB_apiKey+
                    "&language=en-US" +
                    "&page="+Page.toString();
            LOCAL_DB_URI = Contract.ls_nowplaying.uri;
        }
    }
    static class ls_popularLoader extends onlineLoader {
        public ls_popularLoader(Context context, Integer Page,Boolean isOffline) {
            super(context, Page,isOffline); //client is initiate in the parent class

            LOG_TAG = ls_popularLoader.class.getName();
            ERR_DOWNLOADING = "Error Downloading - popular List of movies.";
            ERR_PARSING = "Error Parsing - popular List of movies.";
            uri = "https://api.themoviedb.org/3/movie/popular?" +
                    "api_key=" +
                    Utility.MovieDB_apiKey+
                    "&language=en-US" +
                    "&page="+Page.toString();
            LOCAL_DB_URI = Contract.ls_popular.uri;
        }
    }
    static class ls_TopRatedLoader extends onlineLoader {
        public ls_TopRatedLoader(Context context, Integer Page,Boolean isOffline) {
            super(context, Page,isOffline);
            LOG_TAG = ls_TopRatedLoader.class.getName();
            ERR_DOWNLOADING = "Error Downloading - TopRated List of movies.";
            ERR_PARSING = "Error Parsing - TopRated List of movies.";
            uri = "https://api.themoviedb.org/3/movie/top_rated?" +
                    "api_key=" +
                    Utility.MovieDB_apiKey+
                    "&language=en-US" +
                    "&page="+Page.toString();
            LOCAL_DB_URI = Contract.ls_toprated.uri;
        }
    }
    static class ls_upcomingLoader extends onlineLoader {
        public ls_upcomingLoader(Context context, Integer Page,Boolean isOffline) {
            super(context, Page,isOffline);
            LOG_TAG = ls_upcomingLoader.class.getName();
            ERR_DOWNLOADING = "Error Downloading - upcoming List of movies.";
            ERR_PARSING = "Error Parsing - upcoming List of movies.";
            uri = "https://api.themoviedb.org/3/movie/upcoming?" +
                    "api_key=" +
                    Utility.MovieDB_apiKey+
                    "&language=en-US" +
                    "&page="+Page.toString();
            LOCAL_DB_URI = Contract.ls_upcoming.uri;
        }
    }

    static class MovieReviewsLoader extends AsyncTaskLoader<MovieReviews> {

        String LOG_TAG ="",ERR_DOWNLOADING ="" ,ERR_PARSING ="" ,review_uri="";
        OkHttpClient client;
        final Uri LOCAL_DB_URI = Contract.movie_Reviews.uri;
        Integer movieID;
        Boolean isOffline;
        public MovieReviewsLoader(Context context, Integer movieID, Integer reviewPage,Boolean isOffline) {
            super(context);
            this.isOffline = isOffline;
            LOG_TAG = MovieReviewsLoader.class.getName();
            ERR_DOWNLOADING="Err Downloading @"+LOG_TAG;
            ERR_PARSING = "Err parsing @"+LOG_TAG;
            review_uri =  "https://api.themoviedb.org/3/movie/"+ movieID.toString() + "/reviews?api_key=" +
                    Utility.MovieDB_apiKey +"&language=en-US"+"&page="+reviewPage.toString();
            this.movieID = movieID;
            client = new OkHttpClient();
        }

        @Override
        public MovieReviews loadInBackground() {
            String response="";
            try {
                response = Download(review_uri);
            } catch (IOException e) {
                Log.e(LOG_TAG,ERR_DOWNLOADING);
                e.printStackTrace();
            }
            if(!isOffline)
                Cache(response,movieID);

            return Parse(response);
        }

        String Download(String url) throws IOException {
            if(!isOffline){
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }
            else{

                String []projection = {Contract.movie_Reviews.Columns.review};
                //String []selectionArgs = {};
                Cursor c = getContext().getContentResolver().query(LOCAL_DB_URI,projection,Contract.movie_Reviews.Columns.movie_id+" = "+movieID,null,null);
                if( c!=null && c.moveToFirst()){
                    String json = c.getString(c.getColumnIndex(projection[0]));
                    c.close();
                    return json;
                }
            }
            return "";
        }
        MovieReviews Parse(String json) {
            return MovieReviews.getMovieReviews(json);
        }
        void Cache(final String json , final Integer movieID){
            new AsyncTask<Void, Void, Void>() {
                protected void onPreExecute() {}
                protected Void doInBackground(Void... unused) {
                    if( json!=null && !json.equals("") ){
                        ContentValues v = new ContentValues(1);
                        v.put(Contract.movie_Reviews.Columns.movie_id,movieID);
                        v.put(Contract.movie_Reviews.Columns.review,json);
                        getContext().getContentResolver().insert(LOCAL_DB_URI, v);
                    }
                    return null;
                }
                protected void onPostExecute(Void unused) {}
            }.execute();
            Log.e(LOG_TAG,"Cached !");
        }
    }
    static class MovieVideosLoader extends AsyncTaskLoader<MovieVideos> {

        String LOG_TAG ="",ERR_DOWNLOADING ="" ,ERR_PARSING ="",video_uri="";
        OkHttpClient client;
        final Uri LOCAL_DB_URI = Contract.movie_video.uri;
        Integer movieID;
        Boolean isOffline;
        public MovieVideosLoader(Context context, Integer movieID,Boolean isOffline) {
            super(context);
            this.isOffline = isOffline;
            LOG_TAG = MovieFullDetailsLoader.class.getName();
            ERR_DOWNLOADING="Err Downloading MovieFullDetailsLoader";
            ERR_PARSING = "Err parsing MovieFullDetailsLoader";
            video_uri ="https://api.themoviedb.org/3/movie/"+ movieID.toString() + "/videos?api_key=" +
                    Utility.MovieDB_apiKey +"&language=en-US";
            this.movieID = movieID;
            client = new OkHttpClient();
        }

        @Override
        public MovieVideos loadInBackground() {
            String response="";
            try {
                response = Download(video_uri);
            } catch (IOException e) {
                Log.e(LOG_TAG,ERR_DOWNLOADING);
                e.printStackTrace();
            }
            if(!isOffline)
                Cache(response,movieID);

            return Parse(response);
        }

        String Download(String url) throws IOException {
            if(!isOffline){
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();

            }
            else{
                String []projection = {Contract.movie_video.Columns.video};
                Cursor c = getContext().getContentResolver().query(LOCAL_DB_URI,projection,Contract.movie_video.Columns.movie_id+" = "+movieID,null,null);
                if( c!=null && c.moveToFirst()){
                    String json = c.getString(c.getColumnIndex(projection[0]));
                    c.close();
                    return json;
                }
            }
            return "";
        }

        MovieVideos Parse(String json) {
            return MovieVideos.getMovieVideos(json);
        }

        void Cache(final String json , final Integer movieID){
            new AsyncTask<Void, Void, Void>() {
                protected void onPreExecute() {}
                protected Void doInBackground(Void... unused) {
                    if( json!=null && !json.equals("") ){
                        ContentValues v = new ContentValues(1);
                        v.put(Contract.movie_video.Columns.movie_id,movieID);
                        v.put(Contract.movie_video.Columns.video,json);
                        getContext().getContentResolver().insert(LOCAL_DB_URI, v);
                    }
                    return null;
                }
                protected void onPostExecute(Void unused) {}
            }.execute();
            Log.e(LOG_TAG,"Cached !");
        }
    }
    static class MovieFullDetailsLoader extends AsyncTaskLoader<MovieFullDetails> {
        String LOG_TAG ,ERR_DOWNLOADING ,ERR_PARSING,detial_uri;
        OkHttpClient client;
        final Uri LOCAL_DB_URI = Contract.movie_FullDetail.uri;
        Integer movieID;
        Boolean isOffline;
        public MovieFullDetailsLoader(Context context, Integer movieID,Boolean isOffline) {
            super(context);
            this.isOffline = isOffline;
            LOG_TAG = MovieFullDetailsLoader.class.getName();
            ERR_DOWNLOADING="Err Downloading MovieFullDetailsLoader";
            ERR_PARSING = "Err parsing MovieFullDetailsLoader";
            detial_uri = "https://api.themoviedb.org/3/movie/"+ movieID.toString() +"?api_key=" +
                    Utility.MovieDB_apiKey +"&language=en-US";
            this.movieID = movieID;
            client = new OkHttpClient();
        }
        @Override
        public MovieFullDetails loadInBackground() {
            String response="";
            try {
                response = Download(detial_uri);
            } catch (IOException e) {
                Log.e(LOG_TAG,ERR_DOWNLOADING);
                e.printStackTrace();
            }
            if(!isOffline)
                Cache(response , movieID);
            return Parse(response);
        }

        String Download(String url) throws IOException {
            if(!isOffline){
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            }else{
                String []projection = {Contract.movie_FullDetail.Columns.FullDetail};
                Cursor c = getContext().getContentResolver().query(LOCAL_DB_URI,projection,Contract.movie_FullDetail.Columns.movie_id+" = "+movieID,null,null);
                if( c!=null && c.moveToFirst()){
                    String json = c.getString(c.getColumnIndex(projection[0]));
                    c.close();
                    return json;
                }
            }
            return "";

        }

        MovieFullDetails Parse(String json) {
            return MovieFullDetails.getMovieFullDetails(json);
        }

        void Cache(final String json , final Integer movieID){
            new AsyncTask<Void, Void, Void>() {
                protected void onPreExecute() {}
                protected Void doInBackground(Void... unused) {
                    if( json!=null && !json.equals("") ){
                        ContentValues v = new ContentValues(1);
                        v.put(Contract.movie_FullDetail.Columns.movie_id,movieID);
                        v.put(Contract.movie_FullDetail.Columns.FullDetail,json);
                        getContext().getContentResolver().insert(LOCAL_DB_URI, v);
                    }
                    return null;
                }
                protected void onPostExecute(Void unused) {}
            }.execute();
            Log.e(LOG_TAG,"Cached !");
        }

    }


}
