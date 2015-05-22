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
    public static final int Book_DIR=0;
    public static final int Book_ITEM=1;
    public static final int Category_DIR=2;
    public static final int Category_ITEM=3;
    public static final String AUTHORITY="com.example.databasetest.provider";
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",Book_DIR);
        uriMatcher.addURI(AUTHORITY,"book/#",Book_ITEM);
        uriMatcher.addURI(AUTHORITY,"category",Category_DIR);
        uriMatcher.addURI(AUTHORITY,"category/#",Category_ITEM);
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db=helper.getWritableDatabase();
        int returnInt=0;
        switch (uriMatcher.match(uri))
        {
            case Book_DIR:
                returnInt=db.delete("Book",selection,selectionArgs);
                break;
            case Book_ITEM:
                String bookID=uri.getPathSegments().get(1);
                returnInt=db.delete("Book","id=?",new String[]{bookID});
                break;
            case Category_DIR:
                returnInt=db.delete("Category",selection,selectionArgs);
                break;
            case Category_ITEM:
                String categoryID=uri.getPathSegments().get(1);
                returnInt=db.delete("Category","id=?",new String[]{categoryID});
                break;
            default:
                break;
        }
        return returnInt;
    }

    @Override
    public boolean onCreate() {
        helper=new MyDatabaseOpenHelper(getContext(),"bookStore.db",null,2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri))
        {
            case Book_DIR:
                cursor=db.query("Book",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case Book_ITEM:
                String bookID=uri.getPathSegments().get(1);
                cursor=db.query("Book",projection,"id=?",new String[]{bookID},null,null,sortOrder);
                break;
            case Category_DIR:
                cursor=db.query("Category",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case Category_ITEM:
                String categoryID=uri.getPathSegments().get(1);
                cursor=db.query("Category",projection,"id=?",new String[]{categoryID},null,null,sortOrder);
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        SQLiteDatabase db=helper.getWritableDatabase();
        String sReturn=null;
        switch (uriMatcher.match(uri))
        {
            case Book_DIR:
                sReturn="vnd.android.cursor.dir/vnd.com.example.databasetest.provider/book";
                break;
            case Book_ITEM:
                sReturn="vnd.android.cursor.item/vnd.com.example.databasetest.provider/book";
                break;
            case Category_DIR:
                sReturn="vnd.android.cursor.dir/vnd.com.example.databasetest.provider/category";
                break;
            case Category_ITEM:
                sReturn="vnd.android.cursor.item/vnd.com.example.databasetest.provider/category";
                break;
            default:
                break;
        }
        return  sReturn;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db=helper.getWritableDatabase();
        Uri uriReturn=null;
        switch (uriMatcher.match(uri))
        {
            case Book_DIR:
            case Book_ITEM:
                long bookID=db.insert("Book",null,values);
                uriReturn=Uri.parse("content://"+AUTHORITY+"/book/"+bookID);
                break;
            case Category_DIR:
            case Category_ITEM:
                long categoryID=db.insert("Category",null,values);
                uriReturn= Uri.parse("content://"+AUTHORITY+"/category/"+categoryID);
                break;
            default:
                break;
        }
        return  uriReturn;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db=helper.getWritableDatabase();
        int returnInt=0;
        switch (uriMatcher.match(uri))
        {
            case Book_DIR:
                returnInt=db.update("Book",values,selection,selectionArgs);
                break;
            case Book_ITEM:
                String BookID=uri.getPathSegments().get(1);
                returnInt=db.update("Book",values,"id=?",new String[]{BookID});
                break;
            case Category_DIR:
                returnInt=db.update("Category",values,selection,selectionArgs);
                break;
            case Category_ITEM:
                String categoryID=uri.getPathSegments().get(1);
                returnInt=db.update("Category",values,"id=?",new String[]{categoryID});
                break;
            default:
                break;
        }
        return  returnInt;
    }
}
