package com.example.netflop.data.models;

import com.google.gson.annotations.SerializedName;

public class SpokenLanguage {
    @SerializedName("english_name")
    private String englishName;
    @SerializedName("iso_639_1")
    private String iso639_1;
    private String name;

    public String getEnglishName() { return englishName; }
    public void setEnglishName(String value) { this.englishName = value; }

    public String getIso6391() { return iso639_1; }
    public void setIso6391(String value) { this.iso639_1 = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
}
