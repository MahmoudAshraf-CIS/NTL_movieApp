package com.example.mannas.ntl_movieapp.content;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mannas on 4/6/2017.
 */
public class MovieDetail implements Parcelable {
    public String poster_path
            ,overview
            ,release_date
            ,original_title
            ,original_language
            ,title,
            backdrop_path;

    public Boolean adult,video;
    public Integer id,vote_count;
    public Float popularity,vote_average;

    public String toJsonString(){
        return new Gson().toJson(this);
    }

    public static MovieDetail fromJSON(JSONObject json) throws JSONException {
        return new Gson().fromJson(json.toString(),MovieDetail.class);
    }
    public static String ToJSONstring(MovieDetail movieDetail){
        Gson gson = new Gson();
        return gson.toJson(movieDetail);
    }


    public String getPosterURL(){
        return "http://image.tmdb.org/t/p/w342"+poster_path;
    }

    protected MovieDetail(Parcel in) {
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        original_language = in.readString();
        title = in.readString();
        backdrop_path = in.readString();
        byte adultVal = in.readByte();
        adult = adultVal == 0x02 ? null : adultVal != 0x00;
        byte videoVal = in.readByte();
        video = videoVal == 0x02 ? null : videoVal != 0x00;
        id = in.readByte() == 0x00 ? null : in.readInt();
        vote_count = in.readByte() == 0x00 ? null : in.readInt();
        popularity = in.readByte() == 0x00 ? null : in.readFloat();
        vote_average = in.readByte() == 0x00 ? null : in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(original_title);
        dest.writeString(original_language);
        dest.writeString(title);
        dest.writeString(backdrop_path);
        if (adult == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (adult ? 0x01 : 0x00));
        }
        if (video == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (video ? 0x01 : 0x00));
        }
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        if (vote_count == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(vote_count);
        }
        if (popularity == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(popularity);
        }
        if (vote_average == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(vote_average);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieDetail> CREATOR = new Parcelable.Creator<MovieDetail>() {
        @Override
        public MovieDetail createFromParcel(Parcel in) {
            return new MovieDetail(in);
        }

        @Override
        public MovieDetail[] newArray(int size) {
            return new MovieDetail[size];
        }
    };

}
