package com.example.netflop.data.models.remote.movies;

import java.util.List;

public class CombinedCredit {
    private List<MovieCast> cast;
    private List<MovieCast> crew;
    private int id;

    public List<MovieCast> getCast() { return cast; }
    public void setCast(List<MovieCast> value) { this.cast = value; }

    public List<MovieCast> getCrew() { return crew; }
    public void setCrew(List<MovieCast> value) { this.crew = value; }

    public int getID() { return id; }
    public void setID(int value) { this.id = value; }
}
