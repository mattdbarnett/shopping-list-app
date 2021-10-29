package com.example.cm6226;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "shopping_table";
    private static final String COL1 = "item_name";
    private static final String COL2 = "item_quantity";

    private static final String USER_TABLE_NAME = "user_table";
    private static final String COL3 = "user_name";
    private static final String COL4 = "user_added";
    private static final String COL5 = "user_bought";
    private static final String COL6 = "user_quantity";

    public DatabaseHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    //When DatabaseHelper is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String createTable1 = "CREATE TABLE " + TABLE_NAME + "(item_name text not null, item_quantity text not null)";
        db.execSQL(createTable1);
    }

    //When data is entered
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Inserts an item into the database
    public void addItem(String name, String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, name);
        contentValues.put(COL2, quantity);

        db.insert(TABLE_NAME, null, contentValues);
    }

    //Inserts a user into the database. Ensures there are no other users to prevent issues
    public void addUser(String name, String added, String bought, String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        String createTable2 = "CREATE TABLE " + USER_TABLE_NAME + "(user_name text not null, user_added text not null, user_bought text not null, user_quantity text not null)";
        db.execSQL(createTable2);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL3, name);
        contentValues.put(COL4, added);
        contentValues.put(COL5, bought);
        contentValues.put(COL6, quantity);

        db.insert(USER_TABLE_NAME, null, contentValues);
    }

    //Retrieves all items in the database
    public Cursor getItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //Retrieves current user in the database
    public Cursor getUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + USER_TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
