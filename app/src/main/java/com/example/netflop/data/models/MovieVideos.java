package com.example.netflop.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieVideos {
    private int id;
    private List<Video> results;

    public int getID() { return id; }
    public void setID(int value) { this.id = value; }

    public List<Video> getResults() { return results; }
    public void setResults(List<Video> value) { this.results = value; }
}
