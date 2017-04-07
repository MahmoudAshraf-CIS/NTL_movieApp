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
        switch (mUriMatcher.match(uri)){

            case Contract.TableIdentifier.ls_nowplaying:
                Log.i(LOG_TAG,"query ls_nowplaying ");
                cursor = DB.query(Contract.ls_nowplaying.TABLE_NAME,projection,selection,selectionArgs, null,null,sortOrder);
                //cursor.setNotificationUri(getContext().getContentResolver(),uri);
                cursor.close();
                break;
            case Contract.TableIdentifier.ls_popular:
                break;
            case Contract.TableIdentifier.ls_toprated:
                break;
            case Contract.TableIdentifier.ls_upcoming:
                break;

            case Contract.TableIdentifier.movie_details:
                break;
            case Contract.TableIdentifier.movie_reviews:
                break;
            case Contract.TableIdentifier.movie_similar:
                break;
            case Contract.TableIdentifier.movie_videos:
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
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }



    private static final UriMatcher mUriMatcher;
    static {
        mUriMatcher = new UriMatcher(-1);

        mUriMatcher.addURI(Contract.Authority, Contract.ls_nowplaying.TABLE_NAME, Contract.TableIdentifier.ls_nowplaying);
        mUriMatcher.addURI(Contract.Authority, Contract.ls_popular.TABLE_NAME, Contract.TableIdentifier.ls_popular);
        mUriMatcher.addURI(Contract.Authority, Contract.ls_toprated.TABLE_NAME, Contract.TableIdentifier.ls_toprated);
        mUriMatcher.addURI(Contract.Authority, Contract.ls_upcoming.TABLE_NAME, Contract.TableIdentifier.ls_upcoming);

        mUriMatcher.addURI(Contract.Authority, Contract.movie_details.TABLE_NAME, Contract.TableIdentifier.movie_details);
        mUriMatcher.addURI(Contract.Authority, Contract.movie_reviews.TABLE_NAME, Contract.TableIdentifier.movie_reviews);
        mUriMatcher.addURI(Contract.Authority, Contract.movie_similar.TABLE_NAME, Contract.TableIdentifier.movie_similar);
        mUriMatcher.addURI(Contract.Authority, Contract.movie_videos.TABLE_NAME, Contract.TableIdentifier.movie_videos);

    }

}
