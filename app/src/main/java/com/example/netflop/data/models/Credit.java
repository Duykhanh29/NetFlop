package com.example.netflop.data.models;

import java.util.List;

public class Credit {
    private int id;
    private List<Cast> cast;
    private List<Cast> crew;

    public Credit(int id, List<Cast> cast, List<Cast> crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public Credit() {
    }

    public int getID() { return id; }
    public void setID(int value) { this.id = value; }

    public List<Cast> getCast() { return cast; }
    public void setCast(List<Cast> value) { this.cast = value; }

    public List<Cast> getCrew() { return crew; }
    public void setCrew(List<Cast> value) { this.crew = value; }
}
