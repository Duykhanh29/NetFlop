package com.example.netflop.data.data_source.local_data_source;

public class FavouriteMediaTable {
        public static final String TABLE_NAME="favouriteMedia";
        public static final String COLUMN_ID="id";
        public static final String COLUMN_MEDIA_ID="mediaID";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_MEDIA_TYPE="mediaType";
        public static final String COLUMN_EPISODE="episodeNumber";
        public static final String COLUMN_SEASON="seasonNumber";
        public static final String COLUMN_IMAGE="imageURL";
        public static final String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME +" ("+COLUMN_ID +"  INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_MEDIA_ID+" INTEGER, "+COLUMN_TITLE+" TEXT, "+COLUMN_IMAGE+" TEXT, "+COLUMN_MEDIA_TYPE+" TEXT, "+COLUMN_SEASON+" INTEGER, "+COLUMN_EPISODE+" INTEGER )";

}
