package com.example.mannas.ntl_movieapp.content;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mannas on 4/6/2017.
 */
public class Contract {

    public static final String Authority = "com.example.mannas.ntl_movieapp";
    public static final Uri Base_content_URI = Uri.parse("content://"+Authority);

    public static final class TableIdentifier{
        public static final int ls_nowplaying = 1;
        public static final int ls_popular = 2;
        public static final int ls_toprated = 3;
        public static final int ls_upcoming = 4;


        public static final int movie_FullDetail =6;
        public static final int movie_video =7;
        public static final int movie_Reviews = 8 ;
        public static final int movie_Fav = 9 ;

    }


    public static class ls_nowplaying implements BaseColumns{
        public static final String TABLE_NAME = "now_playing";
        public static final String PATH = TABLE_NAME;
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String page = "page";
            public static final String json ="json";
        }

        public static final String CreationSQL_DDL =
                "CREATE TABLE " + TABLE_NAME + " ( "+
                        Columns.page + " INT PRIMARY KEY ON CONFLICT REPLACE , " +
                        Columns.json + " TEXT );";

        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;

    }
    public static class ls_popular implements BaseColumns{
        public static final String TABLE_NAME = "ls_popular";
        public static final String PATH = TABLE_NAME;
        public static final Uri  uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String page = "page";
            public static final String json ="json";
        }

        public static final String CreationSQL_DDL =
                "CREATE TABLE " + TABLE_NAME + " ( "+
                        Columns.page + " INT PRIMARY KEY ON CONFLICT REPLACE , " +
                        Columns.json + " TEXT );";

        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }
    public static class ls_toprated implements BaseColumns{
        public static final String TABLE_NAME = "ls_toprated";
        public static final String PATH = TABLE_NAME;
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String page = "page";
            public static final String json ="json";
        }

        public static final String CreationSQL_DDL =
                "CREATE TABLE " + TABLE_NAME + " ( "+
                        Columns.page + " INT PRIMARY KEY ON CONFLICT REPLACE , " +
                        Columns.json + " TEXT );";

        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }
    public static class ls_upcoming implements BaseColumns {
        public static final String TABLE_NAME = "ls_upcoming";
        public static final String PATH = TABLE_NAME;
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String page = "page";
            public static final String json ="json";
        }

        public static final String CreationSQL_DDL =
                "CREATE TABLE " + TABLE_NAME + " ( "+
                        Columns.page + " INT PRIMARY KEY ON CONFLICT REPLACE , " +
                        Columns.json + " TEXT );";

        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }


    public static class movie_FullDetail implements BaseColumns{
        public static final String TABLE_NAME = "movie_FullDetail";
        public static final String PATH = TABLE_NAME;
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String movie_id = "movie_id";
            public static final String FullDetail ="FullDetail";
        }
        public static final String CreationSQL_DDL =
                "CREATE TABLE " + TABLE_NAME + " ( "+
                        Columns.movie_id +" INT PRIMARY KEY ON CONFLICT REPLACE ," +
                        Columns.FullDetail + " TEXT );";
        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }
    public static class movie_video implements BaseColumns{
        public static final String TABLE_NAME = "movie_video";
        public static final String PATH = TABLE_NAME;
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String movie_id = "movie_id";
            public static final String video ="video";
        }
        public static final String CreationSQL_DDL =
                "CREATE TABLE " + TABLE_NAME + " ( "+
                        Columns.movie_id +" INT PRIMARY KEY ON CONFLICT REPLACE ," +
                        Columns.video+" TEXT );";
        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }
    public static class movie_Reviews implements BaseColumns{
        public static final String TABLE_NAME = "movie_Reviews";
        public static final String PATH = TABLE_NAME;
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String movie_id = "movie_id";
            public static final String review ="reviews";
        }
        public static final String CreationSQL_DDL =
                "CREATE TABLE " + TABLE_NAME + " ( "+
                        Columns.movie_id +" INT PRIMARY KEY ON CONFLICT REPLACE ," +
                        Columns.review+" TEXT );";
        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }

    public static class movie_Fav implements BaseColumns{
        public static final String TABLE_NAME = "movie_Fav";
        public static final String PATH = TABLE_NAME;
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String movie_id = "movie_id";
            public static final String json= "json";

        }
        public static final String CreationSQL_DDL =
                "CREATE TABLE " + TABLE_NAME + " ( "+Columns.movie_id +" INT PRIMARY KEY ON CONFLICT REPLACE ," +
                        Columns.json + " TEXT );";
        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }


    public static final UriMatcher mUriMatcher;
    static {
        mUriMatcher = new UriMatcher(-1);

        mUriMatcher.addURI(Contract.Authority, ls_nowplaying.PATH, Contract.TableIdentifier.ls_nowplaying);
        mUriMatcher.addURI(Contract.Authority, ls_popular.PATH, Contract.TableIdentifier.ls_popular);
        mUriMatcher.addURI(Contract.Authority, ls_toprated.PATH, Contract.TableIdentifier.ls_toprated);
        mUriMatcher.addURI(Contract.Authority, ls_upcoming.PATH, Contract.TableIdentifier.ls_upcoming);

        mUriMatcher.addURI(Contract.Authority, movie_FullDetail.PATH, TableIdentifier.movie_FullDetail);
        mUriMatcher.addURI(Contract.Authority, movie_video.PATH, TableIdentifier.movie_video);
        mUriMatcher.addURI(Contract.Authority, movie_Reviews.PATH, TableIdentifier.movie_Reviews);
        mUriMatcher.addURI(Contract.Authority, movie_Fav.PATH, TableIdentifier.movie_Fav);
    }

}
