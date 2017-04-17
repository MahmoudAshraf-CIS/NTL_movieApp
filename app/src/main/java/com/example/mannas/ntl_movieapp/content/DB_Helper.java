package com.example.mannas.ntl_movieapp.content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mannas on 4/6/2017.
 */
public class DB_Helper  extends SQLiteOpenHelper {
    public static final String DB_NAME = "movieApp_DB";
    public static final int DB_Version = 1;
    public static final String LOG_TAG = DB_Helper.class.getName();

    public DB_Helper(Context context ) {
        super(context, DB_NAME , null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(  Contract.ls_nowplaying.CreationSQL_DDL);
        db.execSQL(  Contract.ls_popular.CreationSQL_DDL);
        db.execSQL(  Contract.ls_toprated.CreationSQL_DDL);
        db.execSQL(  Contract.ls_upcoming.CreationSQL_DDL);


        db.execSQL(  Contract.movie_FullDetail.CreationSQL_DDL);
        db.execSQL(  Contract.movie_Reviews.CreationSQL_DDL);
        db.execSQL(  Contract.movie_video.CreationSQL_DDL);

        db.execSQL(  Contract.movie_Fav.CreationSQL_DDL);

        Log.i(LOG_TAG,"A new SQL DataBase is deployed !");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){

            db.execSQL(  Contract.ls_nowplaying.DropSQL_DDL);
            db.execSQL(  Contract.ls_popular.DropSQL_DDL);
            db.execSQL(  Contract.ls_toprated.DropSQL_DDL);
            db.execSQL(  Contract.ls_upcoming.DropSQL_DDL);

            db.execSQL(  Contract.movie_Reviews.DropSQL_DDL);
            db.execSQL(  Contract.movie_video.DropSQL_DDL);
            db.execSQL(  Contract.movie_Fav.DropSQL_DDL);
            db.execSQL(  Contract.movie_FullDetail.DropSQL_DDL);

            Log.i(LOG_TAG,"The Old SQL DataBase is Dropped !");
            onCreate(db);
        }
    }


}