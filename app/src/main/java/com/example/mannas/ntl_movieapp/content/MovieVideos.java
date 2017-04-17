package com.example.mannas.ntl_movieapp.content;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Mannas on 4/11/2017.
 */
public class MovieVideos {
    public Integer id;
    public ArrayList<videoResult> results;

    public MovieVideos(){
        id=0;
        results = new ArrayList<>();
    }

    public static MovieVideos getMovieVideos(String json){
        return new Gson().fromJson(json,MovieVideos.class);
    }

    public static String getJSONString (MovieVideos movieVideos){
        return new Gson().toJson(movieVideos);
    }

    public class videoResult{
        public String id ,iso_639_1,iso_3166_1,
                key,name,site,type;
        public Integer size;
    }
}
