package com.example.mannas.ntl_movieapp.content;

import com.google.gson.Gson;

/**
 * Created by Mannas on 4/11/2017.
 */


    /*
    genres
    ...
    production_companies
    ...
    production_countries
    ...
    spoken_languages
    ...
     */

public class MovieFullDetails {
    public String title ,tagline ,status ,release_date,poster_path ,overview ,original_title ,original_language
            ,imdb_id,homepage,belongs_to_collection,backdrop_path;
    public Integer vote_count ,runtime ,id;
    public Double revenue,budget ;
    public Float vote_average ,popularity ;
    public Boolean video ,adult;


    public static MovieFullDetails getMovieFullDetails(String json){
        return new Gson().fromJson(json,MovieFullDetails.class);
    }

    public static String toJSONString(MovieFullDetails fullDetails){
        return new Gson().toJson(fullDetails);
    }

    public String toJSONString( ){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}