package com.example.netflop.data.models.remote.TVs;

import com.example.netflop.data.models.remote.people.Profile;

import java.util.List;

public class TVEpisodeImage {
    private int id;
    private List<Profile> stills;

    public int getId() {
        return id;
    }

    public List<Profile> getStills() {
        return stills;
    }
}
