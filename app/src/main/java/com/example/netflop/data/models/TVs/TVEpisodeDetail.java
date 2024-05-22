package com.example.netflop.data.models.TVs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVEpisodeDetail {
    @SerializedName("air_date")
    private String airDate;
    private List<CrewTV> crew;
    @SerializedName("episode_number")
    private int episodeNumber;
    @SerializedName("guest_stars")
    private List<GuestStar> guestStars;
    private String name;
    private String overview;
    private int id;
    @SerializedName("production_code")
    private String productionCode;
    private int runtime;
    @SerializedName("season_number")
    private int seasonNumber;
    @SerializedName("still_path")
    private String stillPath;
    @SerializedName("vote_average")
    private int voteAverage;
    @SerializedName("vote_count")
    private int voteCount;

    public String getAirDate() {
        return airDate;
    }

    public List<CrewTV> getCrew() {
        return crew;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public List<GuestStar> getGuestStars() {
        return guestStars;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public int getId() {
        return id;
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

    public String getStillPath() {
        return stillPath;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }
}
