package com.example.netflop.data.models.TVs;


import com.example.netflop.data.models.Video;

import java.util.List;

public class TVEpisodeVideo {
    private int id;
    private List<Video> results;

    public int getId() {
        return id;
    }

    public List<Video> getResults() {
        return results;
    }
}
