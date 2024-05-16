package com.example.netflop.data.models;

import com.google.gson.annotations.SerializedName;

public class  Backdrop{
    @SerializedName("aspect_ratio")
    private Double aspectRatio;
    private int height;
    @SerializedName("iso_639_1")
    private String iso639_1;
    @SerializedName("file_path")
    private String filePath;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;
    private int width;

    public Double getAspectRatio() { return aspectRatio; }
    public void setAspectRatio(Double value) { this.aspectRatio = value; }

    public int getHeight() { return height; }
    public void setHeight(int value) { this.height = value; }

    public String getIso6391() { return iso639_1; }
    public void setIso6391(String value) { this.iso639_1 = value; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String value) { this.filePath = value; }

    public Double getVoteAverage() { return voteAverage; }
    public void setVoteAverage(Double value) { this.voteAverage = value; }

    public int getVoteCount() { return voteCount; }
    public void setVoteCount(int value) { this.voteCount = value; }

    public int getWidth() { return width; }
    public void setWidth(int value) { this.width = value; }
}