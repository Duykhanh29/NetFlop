package com.example.netflop.data.models;

import com.google.gson.annotations.SerializedName;

public class Video{
    @SerializedName("iso_639_1")
    private String iso639_1;

    @SerializedName("iso_3166_1")
    private String iso3166_1;
    private String name;
    private String key;
    private String site;
    private int size;
    private String type;
    private Boolean official;
    @SerializedName("published_at")
    private String publishedAt;
    private String id;

    public Video() {
    }

    public Video(String iso639_1, String iso3166_1, String name, String key, String site, int size, String type, Boolean official, String publishedAt, String id) {
        this.iso639_1 = iso639_1;
        this.iso3166_1 = iso3166_1;
        this.name = name;
        this.key = key;
        this.site = site;
        this.size = size;
        this.type = type;
        this.official = official;
        this.publishedAt = publishedAt;
        this.id = id;
    }

    public String getIso639_1() {
        return iso639_1;
    }

    public String getIso3166_1() {
        return iso3166_1;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public Boolean getOfficial() {
        return official;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getId() {
        return id;
    }
}
