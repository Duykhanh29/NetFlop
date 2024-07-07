package com.example.netflop.data.data_source.local_data_source;

public class SearchHistoryTable {
    public static final String TABLE_NAME="searchHistory";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_SEARCH_KEY="searchKey";
    public static final String COLUMN_IS_ADULT="isAdult";
    public static final String COLUMN_SEARCH_TYPE="type";
    public static final String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME
            +" ("+COLUMN_ID +"  INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_SEARCH_KEY+" TEXT, "
            +COLUMN_SEARCH_TYPE+" TEXT, "+COLUMN_IS_ADULT+" INTEGER )";
}
