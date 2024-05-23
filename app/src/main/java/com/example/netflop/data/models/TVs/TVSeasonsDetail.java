package com.example.netflop.data.models.TVs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVSeasonsDetail {
    private int id;
    @SerializedName("air_date")
    private String airDate;
    private List<Episode> episodes;
    private String name;
    private String overview;
    @SerializedName("_id")
    private String tvSeasonsDetailID;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("season_number")
    private int seasonNumber;
    @SerializedName("vote_average")
    private Double voteAverage;

    public int getId() {
        return id;
    }

    public String getAirDate() {
        return airDate;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getTvSeasonsDetailID() {
        return tvSeasonsDetailID;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }
}
