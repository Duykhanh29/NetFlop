package com.example.netflop.data.repository.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.netflop.constants.enums.SearchType;
import com.example.netflop.data.data_source.local_data_source.DatabaseHelper;
import com.example.netflop.data.data_source.local_data_source.SearchHistoryTable;
import com.example.netflop.data.models.local.SearchHistory;

import java.util.ArrayList;
import java.util.List;

public class SearchHistoryRepository {
    private static final String DATABASE_NAME = "database";
    private DatabaseHelper databaseHelper;
    public SearchHistoryRepository(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }
    public boolean insertSearchKey(String searchKey,SearchType searchType,int isAdult){
        SQLiteDatabase database=databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SearchHistoryTable.COLUMN_SEARCH_KEY,searchKey);
        cv.put(SearchHistoryTable.COLUMN_SEARCH_TYPE,searchType.name());
        cv.put(SearchHistoryTable.COLUMN_IS_ADULT,isAdult);
        long result = database.insert(SearchHistoryTable.TABLE_NAME,null, cv);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }
    public boolean deleteSearchKey(int id){
        SQLiteDatabase database=databaseHelper.getWritableDatabase();
        long result = database.delete(SearchHistoryTable.TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }
//    public boolean updateSearchKey(int id){
//        SQLiteDatabase database=databaseHelper.getWritableDatabase();
//
//        if(result == -1){
//            return false;
//        }else {
//            return true;
//        }
//    }
        private Cursor getAllSearchHistory() {
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            return db.rawQuery("SELECT * FROM " + SearchHistoryTable.TABLE_NAME, null);
    }
    public List<SearchHistory> getListSearchHistory(){
        List<SearchHistory> list=new ArrayList<>();
        Cursor cursor=getAllSearchHistory();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(SearchHistoryTable.COLUMN_ID));
                String searchKey = cursor.getString(cursor.getColumnIndexOrThrow(SearchHistoryTable.COLUMN_SEARCH_KEY));
                String type =cursor.getString(cursor.getColumnIndexOrThrow(SearchHistoryTable.COLUMN_SEARCH_TYPE));
                int isAdult=cursor.getInt(cursor.getColumnIndexOrThrow(SearchHistoryTable.COLUMN_IS_ADULT));
                    // get other attributes
                SearchHistory searchHistory=new SearchHistory(id, SearchType.valueOf(type),searchKey,isAdult);
                list.add(searchHistory);
                } while (cursor.moveToNext());
            }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
}
