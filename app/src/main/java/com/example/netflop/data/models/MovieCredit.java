package com.example.netflop.data.models;

import java.util.List;

public class MovieCredit {
    private List<MovieCast> cast;
    private List<MovieCast> crew;
    private Long id;

    public List<MovieCast> getCast() { return cast; }
    public void setCast(List<MovieCast> value) { this.cast = value; }

    public List<MovieCast> getCrew() { return crew; }
    public void setCrew(List<MovieCast> value) { this.crew = value; }

    public Long getID() { return id; }
    public void setID(Long value) { this.id = value; }
}
