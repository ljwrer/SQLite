package com.example.databasetest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Ray on 2015/5/20.
 */
public class DatabaseProvider extends ContentProvider {
    private MyDatabaseOpenHelper helper;
    public static UriMatcher uriMatcher;
    public static final int TABLE1_DIR=0;
    public static final int TABLE1_ITEM=1;
    public static final int TABLE2_DIR=2;
    public static final int TABLE2_ITEM=3;
    public static final String AUTHORITY="com.example.databasetest.provider";
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"Book",TABLE1_DIR);
        uriMatcher.addURI(AUTHORITY,"Book/#",TABLE1_ITEM);
        uriMatcher.addURI(AUTHORITY,"Category",TABLE2_DIR);
        uriMatcher.addURI(AUTHORITY,"Category/#",TABLE2_ITEM);
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public boolean onCreate() {
        helper=new MyDatabaseOpenHelper(getContext(),"bookStore.db",null,2)
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        db=helper.getWritableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri))
        {
            case TABLE1_DIR:
                cursor=db.query("Book",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case TABLE1_ITEM:
                String bookID=uri.getPathSegments().get(1);
                cursor=db.query("Book",projection,"id=?",new String[]{bookID},null,null,sortOrder);
                break;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
