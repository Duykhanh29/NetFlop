package com.example.netflop.data.models.local;

import com.example.netflop.constants.enums.SearchType;

public class SearchHistory {
    private int id;
    private SearchType searchType;
    private String searchKey;
    private int isAdult;

    public SearchHistory(int id, SearchType searchType, String searchKey, int isAdult) {
        this.id = id;
        this.searchType = searchType;
        this.searchKey = searchKey;
        this.isAdult = isAdult;
    }
    public SearchHistory(SearchType searchType, String searchKey, int isAdult) {
        this.searchType = searchType;
        this.searchKey = searchKey;
        this.isAdult = isAdult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public int isAdult() {
        return isAdult;
    }

    public void setAdult(int adult) {
        isAdult = adult;
    }
}
