package com.example.netflop.data.repository.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.netflop.constants.enums.SearchType;
import com.example.netflop.constants.enums.TypeOfMedia;
import com.example.netflop.data.data_source.local_data_source.DatabaseHelper;
import com.example.netflop.data.data_source.local_data_source.FavouriteMediaTable;
import com.example.netflop.data.data_source.local_data_source.SearchHistoryTable;
import com.example.netflop.data.models.local.FavouriteMedia;
import com.example.netflop.data.models.local.SearchHistory;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMediaRepository {
    private DatabaseHelper databaseHelper;
    public FavouriteMediaRepository(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }
    public boolean insertFavouriteMedia(int mediaID,String title, TypeOfMedia typeOfMedia, Integer seasonNumber,Integer episodeNumber,String image){
        SQLiteDatabase database=databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FavouriteMediaTable.COLUMN_MEDIA_ID,mediaID);
        cv.put(FavouriteMediaTable.COLUMN_TITLE,title);
        cv.put(FavouriteMediaTable.COLUMN_MEDIA_TYPE,typeOfMedia.name());
        cv.put(FavouriteMediaTable.COLUMN_EPISODE,episodeNumber);
        cv.put(FavouriteMediaTable.COLUMN_SEASON,seasonNumber);
        cv.put(FavouriteMediaTable.COLUMN_IMAGE,image);
        long result = database.insert(FavouriteMediaTable.TABLE_NAME,null, cv);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }
    public boolean deleteFavouriteMedia(int id){
        SQLiteDatabase database=databaseHelper.getWritableDatabase();
        long result = database.delete(FavouriteMediaTable.TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
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
    private Cursor getAllFavouriteMedia() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + FavouriteMediaTable.TABLE_NAME, null);
    }
    public List<FavouriteMedia> getListFavouriteMedia(){
        List<FavouriteMedia> list=new ArrayList<>();
        Cursor cursor=getAllFavouriteMedia();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String typeOfMedia=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_TYPE));
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_TITLE));
                    int mediaID =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_ID));
                    String imageURL=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_IMAGE));
                    Integer seasonNumber =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_SEASON));
                    Integer episodeNumber=cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_EPISODE));
                    // get other attributes
                    FavouriteMedia favouriteMedia=new FavouriteMedia(id,mediaID,TypeOfMedia.valueOf(typeOfMedia),imageURL,seasonNumber,episodeNumber,title);
                    list.add(favouriteMedia);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    public List<FavouriteMedia> getListFavouriteMovie(){
        List<FavouriteMedia> list=new ArrayList<>();
        Cursor cursor=getAllFavouriteMedia();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String typeOfMedia=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_TYPE));
                if(TypeOfMedia.valueOf(typeOfMedia)==TypeOfMedia.movie){
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_TITLE));
                    int mediaID =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_ID));
                    String imageURL=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_IMAGE));
                    Integer seasonNumber =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_SEASON));
                    Integer episodeNumber=cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_EPISODE));
                    // get other attributes
                    FavouriteMedia favouriteMedia=new FavouriteMedia(id,mediaID,TypeOfMedia.valueOf(typeOfMedia),imageURL,seasonNumber,episodeNumber,title);
                    list.add(favouriteMedia);
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
    public List<FavouriteMedia> getListFavouritePeople(){
        List<FavouriteMedia> list=new ArrayList<>();
        Cursor cursor=getAllFavouriteMedia();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String typeOfMedia=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_TYPE));
                if(TypeOfMedia.valueOf(typeOfMedia)==TypeOfMedia.person){
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_TITLE));
                    int mediaID =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_ID));
                    String imageURL=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_IMAGE));
                    Integer seasonNumber =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_SEASON));
                    Integer episodeNumber=cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_EPISODE));
                    // get other attributes
                    FavouriteMedia favouriteMedia=new FavouriteMedia(id,mediaID,TypeOfMedia.valueOf(typeOfMedia),imageURL,seasonNumber,episodeNumber,title);
                    list.add(favouriteMedia);
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
    public List<FavouriteMedia> getListFavouriteTVs(){
        List<FavouriteMedia> list=new ArrayList<>();
        Cursor cursor=getAllFavouriteMedia();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String typeOfMedia=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_TYPE));
                if(TypeOfMedia.valueOf(typeOfMedia)!=TypeOfMedia.movie&&TypeOfMedia.valueOf(typeOfMedia)!=TypeOfMedia.person){
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_TITLE));
                    int mediaID =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_ID));
                    String imageURL=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_IMAGE));
                    Integer seasonNumber =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_SEASON));
                    Integer episodeNumber=cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_EPISODE));
                    // get other attributes
                    FavouriteMedia favouriteMedia=new FavouriteMedia(id,mediaID,TypeOfMedia.valueOf(typeOfMedia),imageURL,seasonNumber,episodeNumber,title);
                    list.add(favouriteMedia);
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
    public List<FavouriteMedia> getListFavouriteTVSeries(){
        List<FavouriteMedia> list=new ArrayList<>();
        Cursor cursor=getAllFavouriteMedia();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String typeOfMedia=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_TYPE));
                if(TypeOfMedia.valueOf(typeOfMedia)==TypeOfMedia.TVSeries){
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_TITLE));
                    int mediaID =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_ID));
                    String imageURL=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_IMAGE));
                    Integer seasonNumber =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_SEASON));
                    Integer episodeNumber=cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_EPISODE));
                    // get other attributes
                    FavouriteMedia favouriteMedia=new FavouriteMedia(id,mediaID,TypeOfMedia.valueOf(typeOfMedia),imageURL,seasonNumber,episodeNumber,title);
                    list.add(favouriteMedia);
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
    public List<FavouriteMedia> getListFavouriteTVSeason(){
        List<FavouriteMedia> list=new ArrayList<>();
        Cursor cursor=getAllFavouriteMedia();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String typeOfMedia=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_TYPE));
                if(TypeOfMedia.valueOf(typeOfMedia)==TypeOfMedia.TVSeason){
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_TITLE));
                    int mediaID =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_ID));
                    String imageURL=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_IMAGE));
                    Integer seasonNumber =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_SEASON));
                    Integer episodeNumber=cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_EPISODE));
                    // get other attributes
                    FavouriteMedia favouriteMedia=new FavouriteMedia(id,mediaID,TypeOfMedia.valueOf(typeOfMedia),imageURL,seasonNumber,episodeNumber,title);
                    list.add(favouriteMedia);
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
    public List<FavouriteMedia> getListFavouriteTVEpisode(){
        List<FavouriteMedia> list=new ArrayList<>();
        Cursor cursor=getAllFavouriteMedia();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String typeOfMedia=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_TYPE));
                if(TypeOfMedia.valueOf(typeOfMedia)==TypeOfMedia.TVEpisode){
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_ID));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_TITLE));
                    int mediaID =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_MEDIA_ID));
                    String imageURL=cursor.getString(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_IMAGE));
                    Integer seasonNumber =cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_SEASON));
                    Integer episodeNumber=cursor.getInt(cursor.getColumnIndexOrThrow(FavouriteMediaTable.COLUMN_EPISODE));
                    // get other attributes
                    FavouriteMedia favouriteMedia=new FavouriteMedia(id,mediaID,TypeOfMedia.valueOf(typeOfMedia),imageURL,seasonNumber,episodeNumber,title);
                    list.add(favouriteMedia);
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }
}
