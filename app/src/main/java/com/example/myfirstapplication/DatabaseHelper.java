package com.example.myfirstapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "shopMasterlist.db", null, 39);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists items(id integer primary key autoincrement, name text,price int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists items");
        onCreate(sqLiteDatabase);
    }
    public void insertData(Data data){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("name",data.getName());
        contentValues.put("price",data.getPrice());
        long insert = sqLiteDatabase.insert("items", null, contentValues);
        Log.e(TAG,"insertData: "+insert);
    }
    public Cursor readData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        String qry="select * from items order by id";
        Cursor cursor=sqLiteDatabase.rawQuery(qry,null);
        return cursor;
    }
    public void updateData(Data data){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",data.getName());
        contentValues.put("price",data.getPrice());
        int index=data.getIndex();
        int items = sqLiteDatabase.update("items", contentValues,"id='"+index+"'",null);
        Log.e(TAG,"updateData: "+items);
    }
    public void deleteData(Data data){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        int items = sqLiteDatabase.delete("items", "id in("+data.getDeleteList()+")", null);
        Log.e(TAG,"deleteData: "+items);
    }
}
