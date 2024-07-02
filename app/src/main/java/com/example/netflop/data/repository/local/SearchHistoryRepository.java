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
    private DatabaseHelper databaseHelper;
    public SearchHistoryRepository(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public boolean insertOrUpdateSearchKey(String searchKey, SearchType searchType, int isAdult) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        // Check if an entry with the same searchKey, searchType, and isAdult exists
        String selection = SearchHistoryTable.COLUMN_SEARCH_KEY + " = ? AND " +
                SearchHistoryTable.COLUMN_SEARCH_TYPE + " = ? AND " +
                SearchHistoryTable.COLUMN_IS_ADULT + " = ?";
        String[] selectionArgs = { searchKey, searchType.name(), String.valueOf(isAdult) };

        Cursor cursor = database.query(SearchHistoryTable.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();

        if (exists) {
            return updateSearchKey(searchKey, searchType, isAdult);
        } else {
            return insertSearchKey(searchKey, searchType, isAdult);
        }
    }

    public boolean deleteAndInsertSearchKey(String searchKey, SearchType searchType, int isAdult) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        // Check if an entry with the same searchKey, searchType, and isAdult exists
        String selection = SearchHistoryTable.COLUMN_SEARCH_KEY + " = ? AND " +
                SearchHistoryTable.COLUMN_SEARCH_TYPE + " = ? AND " +
                SearchHistoryTable.COLUMN_IS_ADULT + " = ?";
        String[] selectionArgs = { searchKey, searchType.name(), String.valueOf(isAdult) };

        Cursor cursor = database.query(SearchHistoryTable.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(SearchHistoryTable.COLUMN_ID));
            cursor.close();
            // Delete the old entry
            deleteSearchKey(id);
        } else if (cursor != null) {
            cursor.close();
        }

        // Insert the new entry
        return insertSearchKey(searchKey, searchType, isAdult);
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
    private boolean updateSearchKey(String searchKey, SearchType searchType, int isAdult) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SearchHistoryTable.COLUMN_SEARCH_KEY, searchKey);
        cv.put(SearchHistoryTable.COLUMN_SEARCH_TYPE, searchType.name());
        cv.put(SearchHistoryTable.COLUMN_IS_ADULT, isAdult);

        String selection = SearchHistoryTable.COLUMN_SEARCH_KEY + " = ? AND " +
                SearchHistoryTable.COLUMN_SEARCH_TYPE + " = ? AND " +
                SearchHistoryTable.COLUMN_IS_ADULT + " = ?";
        String[] selectionArgs = { searchKey, searchType.name(), String.valueOf(isAdult) };

        long result = database.update(SearchHistoryTable.TABLE_NAME, cv, selection, selectionArgs);
        return result != -1;
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
    public boolean clearSearchHistory() {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        int result = database.delete(SearchHistoryTable.TABLE_NAME, null, null);
        return result != -1;
    }
}
