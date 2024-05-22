package com.example.netflop.data.models.TVs;

import com.google.gson.annotations.SerializedName;

public class Season {
    @SerializedName("air_date")
    private String airDate;
    @SerializedName("episode_count")
    private int episodeCount;
    private int id;
    private String name;
    private String overview;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("season_number")
    private int seasonNumber;
    @SerializedName("vote_average")
    private int voteAverage;

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }
}
