package com.example.mannas.ntl_movieapp.content;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Mannas on 4/6/2017.
 */
public class OfflineContentProvider extends android.content.ContentProvider {
    DB_Helper mDBHelper;
    static final String LOG_TAG = OfflineContentProvider.class.getName();

    @Override
    public boolean onCreate() {
        mDBHelper = new DB_Helper(getContext());
        return true;
    }

    /**
     *
     * @param uri The URI, using the content:// scheme, for the content to retrieve.
     * @param projection String: A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param selection String: A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given URI.
     * @param selectionArgs String: You may include ?s in selection, which will be replaced by the values from selectionArgs, in the order that they appear in the selection. The values will be bound as Strings.
     * @param sortOrder String: How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered.
     * @return
     */
    @Nullable
    @Override
    public Cursor query( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase DB = mDBHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (Contract.mUriMatcher.match(uri)){

            case Contract.TableIdentifier.ls_nowplaying:
                cursor = DB.query(Contract.ls_nowplaying.TABLE_NAME,projection,selection,selectionArgs, null,null,sortOrder);
                break;
            case Contract.TableIdentifier.ls_popular:
                cursor = DB.query(Contract.ls_popular.TABLE_NAME,projection,selection,selectionArgs, null,null,sortOrder);
                break;
            case Contract.TableIdentifier.ls_toprated:
                cursor = DB.query(Contract.ls_toprated.TABLE_NAME,projection,selection,selectionArgs, null,null,sortOrder);
                break;
            case Contract.TableIdentifier.ls_upcoming:
                cursor = DB.query(Contract.ls_upcoming.TABLE_NAME,projection,selection,selectionArgs, null,null,sortOrder);
                break;

            case Contract.TableIdentifier.movie_FullDetail:
                cursor = DB.query(Contract.movie_FullDetail.TABLE_NAME,projection,selection,selectionArgs, null,null,sortOrder);
                break;
            case Contract.TableIdentifier.movie_Reviews:
                cursor = DB.query(Contract.movie_Reviews.TABLE_NAME,projection,selection,selectionArgs, null,null,sortOrder);
                break;
            case Contract.TableIdentifier.movie_video:
                cursor = DB.query(Contract.movie_video.TABLE_NAME,projection,selection,selectionArgs, null,null,sortOrder);
                break;
            case Contract.TableIdentifier.movie_Fav:
                cursor = DB.query(Contract.movie_Fav.TABLE_NAME,projection,selection,selectionArgs, null,null,sortOrder);
                break;
        }
        return cursor;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (Contract.mUriMatcher.match(uri)) {
            case Contract.TableIdentifier.ls_nowplaying:
                mDBHelper.getWritableDatabase().insert(Contract.ls_nowplaying.TABLE_NAME,null,contentValues);
                break;
            case Contract.TableIdentifier.ls_popular:
                mDBHelper.getWritableDatabase().insert(Contract.ls_popular.TABLE_NAME,null,contentValues);
                break;
            case Contract.TableIdentifier.ls_toprated :
                mDBHelper.getWritableDatabase().insert(Contract.ls_toprated.TABLE_NAME,null,contentValues);
                break;
            case Contract.TableIdentifier.ls_upcoming :
                mDBHelper.getWritableDatabase().insert(Contract.ls_upcoming.TABLE_NAME,null,contentValues);
                break;

            case Contract.TableIdentifier.movie_FullDetail:
                mDBHelper.getWritableDatabase().insert(Contract.movie_FullDetail.TABLE_NAME,null,contentValues);
                break;
            case Contract.TableIdentifier.movie_Reviews:
                mDBHelper.getWritableDatabase().insert(Contract.movie_Reviews.TABLE_NAME,null,contentValues);
                break;
            case Contract.TableIdentifier.movie_video:
                mDBHelper.getWritableDatabase().insert(Contract.movie_video.TABLE_NAME,null,contentValues);
                break;
            case Contract.TableIdentifier.movie_Fav:
                mDBHelper.getWritableDatabase().insert(Contract.movie_Fav.TABLE_NAME,null,contentValues);
                break;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String whereClauses, String[] whereArgs) {
        if(Contract.mUriMatcher.match(uri) == Contract.TableIdentifier.movie_Fav)
            return mDBHelper.getWritableDatabase().delete(Contract.movie_Fav.TABLE_NAME,whereClauses,whereArgs);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }



}
