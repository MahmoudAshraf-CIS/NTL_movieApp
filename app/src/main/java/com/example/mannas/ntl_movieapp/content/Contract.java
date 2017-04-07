package com.example.mannas.ntl_movieapp.content;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mannas on 4/6/2017.
 */
public class Contract {

    public static final String Authority = "com.example.mannas.ntl_movieapp";
    public static final Uri Base_content_URI = Uri.parse("content://"+Authority);

    public static final class TableIdentifier{

        public static final int ls_nowplaying = 2;
        public static final int ls_popular = 3;
        public static final int ls_toprated = 4;
        public static final int ls_upcoming = 5;

        public static final int movie_details = 6 ;
        public static final int movie_reviews = 7;
        public static final int movie_similar = 8;
        public static final int movie_videos = 9;

    }


    public static class ls_nowplaying implements BaseColumns{
        public static final String TABLE_NAME = "ls_nowplaying";
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String _id = "_id";
            public static final String time ="time";
            public static final String json ="json";
        }

        public static final String CreationSQL_DDL =
                "CREATE TABLE (" + TABLE_NAME +
                        " _id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " time TIME    NOT NULL," +
                        " json TEXT" +
                        ");";

        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }
    public static class ls_popular implements BaseColumns{
        public static final String TABLE_NAME = "ls_popular";
        public static final Uri  uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String _id = "_id";
            public static final String time ="time";
            public static final String json ="json";
        }

        public static final String CreationSQL_DDL =
                "CREATE TABLE (" + TABLE_NAME +
                        " _id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " time TIME    NOT NULL," +
                        " json TEXT" +
                        ");";

        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }
    public static class ls_toprated implements BaseColumns{
        public static final String TABLE_NAME = "ls_toprated";
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String _id = "_id";
            public static final String time ="time";
            public static final String json ="json";
        }

        public static final String CreationSQL_DDL =
                "CREATE TABLE (" + TABLE_NAME +
                        " _id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " time TIME    NOT NULL," +
                        " json TEXT" +
                        ");";

        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }
    public static class ls_upcoming implements BaseColumns{
        public static final String TABLE_NAME = "ls_upcoming";
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String _id = "_id";
            public static final String time ="time";
            public static final String json ="json";
        }

        public static final String CreationSQL_DDL =
                "CREATE TABLE (" + TABLE_NAME +
                        " _id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " time TIME    NOT NULL," +
                        " json TEXT" +
                        ");";

        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }


    public static class movie_details implements BaseColumns{
        public static final String TABLE_NAME = "movie_details";
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String _id = "movie_id";
            public static final String json ="json";
        }

        public static final String CreationSQL_DDL =
                "CREATE TABLE (" + TABLE_NAME +
                        " movie_id  INTEGER PRIMARY KEY , " +
                        " json TEXT" +
                        ");";

        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }
    public static class movie_reviews implements BaseColumns{
        public static final String TABLE_NAME = "movie_reviews";
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String _id = "movie_id";
            public static final String json ="json";
        }

        public static final String CreationSQL_DDL =
                "CREATE TABLE (" + TABLE_NAME +
                        " movie_id  INTEGER PRIMARY KEY , " +
                        " json TEXT" +
                        ");";

        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }
    public static class movie_similar implements BaseColumns{
        public static final String TABLE_NAME = "movie_similar";
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String _id = "movie_id";
            public static final String json ="json";
        }

        public static final String CreationSQL_DDL =
                "CREATE TABLE (" + TABLE_NAME +
                        " movie_id  INTEGER PRIMARY KEY , " +
                        " json TEXT" +
                        ");";

        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }
    public static class movie_videos implements BaseColumns{
        public static final String TABLE_NAME = "movie_videos";
        public static final Uri uri = Base_content_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static class Columns {
            public static final String _id = "movie_id";
            public static final String json ="json";
        }

        public static final String CreationSQL_DDL =
                "CREATE TABLE (" + TABLE_NAME +
                        " movie_id  INTEGER PRIMARY KEY , " +
                        " json TEXT" +
                        ");";

        public static final String DropSQL_DDL = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    }
}
