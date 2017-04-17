package com.example.mannas.ntl_movieapp.content;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Mannas on 4/11/2017.
 */
public class MovieReviews {
    public Integer id,page,total_pages,total_results;
    public ArrayList<userReview> results;

    public MovieReviews(){
        id=page=total_pages=total_results=0;
        results = new ArrayList<>();
    }

    public static MovieReviews getMovieReviews(String json){
        return new Gson().fromJson(json,MovieReviews.class);
    }

    public static String getJSONString (MovieReviews movieReviews){
        return new Gson().toJson(movieReviews);
    }

    public static class userReview{
       public String id,author,content,url;
    }

}
