package com.example.netflop.data.data_source.local_data_source;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NetFlopDatabase";
    private Context context;
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(SearchHistoryTable.CREATE_TABLE);
            sqLiteDatabase.execSQL(FavouriteMediaTable.CREATE_TABLE);
            Log.d("LOG", "Tables created successfully.");
        } catch (Exception e) {
            Log.e("LOG", "Error creating tables: " + e.getMessage());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SearchHistoryTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FavouriteMediaTable.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
