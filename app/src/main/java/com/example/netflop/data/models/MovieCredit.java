package com.example.netflop.data.models;

import java.util.List;

public class MovieCredit {
    private List<Cast> cast;
    private List<Cast> crew;
    private Long id;

    public List<Cast> getCast() { return cast; }
    public void setCast(List<Cast> value) { this.cast = value; }

    public List<Cast> getCrew() { return crew; }
    public void setCrew(List<Cast> value) { this.crew = value; }

    public Long getID() { return id; }
    public void setID(Long value) { this.id = value; }
}
