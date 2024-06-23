package com.example.netflop.data.models.remote.movies;

import com.google.gson.annotations.SerializedName;

public class ProductionCountry {
    @SerializedName("iso_3166_1")
    private String iso3166_1;
    private String name;

    public String getIso31661() { return iso3166_1; }
    public void setIso31661(String value) { this.iso3166_1 = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
}
