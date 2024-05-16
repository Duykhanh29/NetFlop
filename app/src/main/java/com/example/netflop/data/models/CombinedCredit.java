package com.example.netflop.data.models;

import java.util.List;

public class CombinedCredit {
    private List<Cast> cast;
    private List<Cast> crew;
    private int id;

    public List<Cast> getCast() { return cast; }
    public void setCast(List<Cast> value) { this.cast = value; }

    public List<Cast> getCrew() { return crew; }
    public void setCrew(List<Cast> value) { this.crew = value; }

    public int getID() { return id; }
    public void setID(int value) { this.id = value; }
}
