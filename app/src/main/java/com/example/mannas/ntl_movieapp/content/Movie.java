package com.example.mannas.ntl_movieapp.content;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mannas on 4/6/2017.
 */
public class Movie  {
    public String poster_path
            ,overview
            ,release_date
            ,original_title
            ,original_language
            ,title,
            backdrop_path;

    public Boolean adult,video;
    public Integer id,vote_count;
    public Double popularity,vote_average;

    public static Movie ToMovie(JSONObject json) throws JSONException {
        return new Gson().fromJson(json.toString(),Movie.class);

//        Movie m = new Movie();
//            if(json.has("poster_path"))
//                m.poster_path = json.getString("poster_path");
//            if(json.has("adult"))
//                m.adult = json.getBoolean("adult");
//            if(json.has("overview"))
//                m.overview = json.getString("overview");
//            if(json.has("release_date"))
//                m.release_date = json.getString("release_date");
//            if(json.has("id"))
//                m.id = json.getInt("id");
//            if(json.has("original_title"))
//                m.original_title = json.getString("original_title");
//            if(json.has("original_language"))
//                m.original_language = json.getString("original_language");
//            if(json.has("title"))
//                m.title = json.getString("title");
//            if(json.has("backdrop_path"))
//                m.backdrop_path = json.getString("backdrop_path");
//            if(json.has("popularity"))
//                m.popularity = json.getDouble("popularity");
//            if(json.has("vote_count"))
//                m.vote_count = json.getInt("vote_count");
//            if(json.has("video"))
//                m.video = json.getBoolean("video");
//            if(json.has("vote_average"))
//                m.vote_average = json.getDouble("vote_average");
//        return m;
    }
    public static String ToJSONstring(Movie movie){
        Gson gson = new Gson();
        return gson.toJson(movie);
    }

    public String getPosterURL(){
        return "http://image.tmdb.org/t/p/w342"+poster_path;
    }
}
