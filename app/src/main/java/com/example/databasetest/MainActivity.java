package com.example.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    private MyDatabaseOpenHelper helper;
    Button add;
    Button update;
    Button delete;
    Button query;
    TextView result;
    Button replace;
    Button clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper=new MyDatabaseOpenHelper(MainActivity.this,"bookStore.db",null,2);
        Button button=(Button)findViewById(R.id.create_database);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.getWritableDatabase();
            }
        });
        add=(Button)findViewById(R.id.add_data);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                SQLiteDatabase db=helper.getWritableDatabase();
                values.put("name","The Da Vinci Code");
                values.put("author","Dan Brown");
                values.put("pages",454);
                values.put("price",16.96);
                db.insert("Book", null, values);
                values.clear();
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values);
                values.clear();
                Toast.makeText(MainActivity.this,"insert success",Toast.LENGTH_SHORT).show();
            }
        });
        update=(Button)findViewById(R.id.update_data);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                SQLiteDatabase db=helper.getWritableDatabase();
                values.put("price",10.99);
                db.update("Book", values, "name=?", new String[]{"The Da Vinci Code"});
                Toast.makeText(MainActivity.this,"update success",Toast.LENGTH_SHORT).show();
            }
        });
        delete=(Button)findViewById(R.id.delete_data);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=helper.getWritableDatabase();
                db.delete("Book","pages>?",new String[]{"500"});
                Toast.makeText(MainActivity.this,"delete success",Toast.LENGTH_SHORT).show();
            }
        });
        query=(Button)findViewById(R.id.query_data);
        result=(TextView)findViewById(R.id.query_result);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=helper.getWritableDatabase();
                Cursor cursor=db.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        result.setText(author+" "+name+" "+pages+" "+price);
                    }
                    while (cursor.moveToNext());

                }
            }
        });
        replace =(Button)findViewById(R.id.replace_data);
        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=helper.getWritableDatabase();
                db.beginTransaction();
                try{
                    db.delete("Book",null,null);
                    if(true)
                    {
                        throw new NullPointerException();
                    }
                    ContentValues values=new ContentValues();
                    values.put("name","The Da Vinci Code");
                    values.put("author","Dan Brown");
                    values.put("pages",454);
                    values.put("price",16.96);
                    db.insert("Book", null, values);
                    values.clear();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    db.endTransaction();
                }
            }
        });
        clear=(Button)findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=helper.getWritableDatabase();
                db.execSQL("drop table if exists Book");
                db.execSQL("drop table if exists Category");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
