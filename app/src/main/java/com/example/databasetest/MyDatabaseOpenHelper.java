package com.example.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ray on 2015/5/19.
 */
public class MyDatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String books="create table Book("
            +"id integer primary key autoincrement, "
            +"author text, "+"price real, "
            +"pages integer, "
            +"name text, "
            +"category_id integer)";
    public static final String category="create table Category("
            +"id integer primary key autoincrement, "
            +"category_name text, "
            +"category_code integer)";
    private Context mContext;
    public MyDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(books);
        db.execSQL(category);
//        Toast.makeText(mContext,"create database success",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion)
        {
            case 1:
                db.execSQL(category);
            case 2:
                db.execSQL("alter table Book add column category_id integer");
            default:
        }
    }
}
