package com.example.netflop.data.models;

import com.google.gson.annotations.SerializedName;

class Profile{
    @SerializedName("aspect_ratio")
    private Double aspectRatio;
    private int height;
    @SerializedName("iso_639_1")
    private Object iso639_1;
    @SerializedName("file_path")
    private String filePath;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;
    private int width;

    public Double getAspectRatio() {
        return aspectRatio;
    }

    public int getHeight() {
        return height;
    }

    public Object getIso639_1() {
        return iso639_1;
    }

    public String getFilePath() {
        return filePath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public int getWidth() {
        return width;
    }

    public Profile(Double aspectRatio, int height, Object iso639_1, String filePath, Double voteAverage, int voteCount, int width) {
        this.aspectRatio = aspectRatio;
        this.height = height;
        this.iso639_1 = iso639_1;
        this.filePath = filePath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.width = width;
    }

    public Profile() {
    }
}
