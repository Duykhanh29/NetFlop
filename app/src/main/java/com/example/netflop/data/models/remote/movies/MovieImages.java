package com.example.netflop.data.models.remote.movies;

import java.util.List;

public class MovieImages {
    private List<Backdrop> backdrops;
    private Long id;
    private List<Backdrop> logos;
    private List<Backdrop> posters;

    public List<Backdrop> getBackdrops() { return backdrops; }
    public void setBackdrops(List<Backdrop> value) { this.backdrops = value; }

    public Long getID() { return id; }
    public void setID(Long value) { this.id = value; }

    public List<Backdrop> getLogos() { return logos; }
    public void setLogos(List<Backdrop> value) { this.logos = value; }

    public List<Backdrop> getPosters() { return posters; }
    public void setPosters(List<Backdrop> value) { this.posters = value; }
}
