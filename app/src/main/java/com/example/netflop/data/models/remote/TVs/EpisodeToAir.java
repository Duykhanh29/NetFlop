package com.example.netflop.data.models.remote.TVs;

import com.google.gson.annotations.SerializedName;

public class EpisodeToAir {
    private int id;
    private String overview;
    private String name;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("air_date")
    private String airDate;
    @SerializedName("episode_number")
    private int episodeNumber;
    @SerializedName("episode_type")
    private String episodeType;
    @SerializedName("production_code")
    private String productionCode;
    private int runtime;
    @SerializedName("season_number")
    private int seasonNumber;
    @SerializedName("show_id")
    private int showID;
    @SerializedName("still_path")
    private String stillPath;

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getName() {
        return name;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getAirDate() {
        return airDate;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeType() {
        return episodeType;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public int getRuntime() {
        return runtime;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getShowID() {
        return showID;
    }

    public String getStillPath() {
        return stillPath;
    }
}
