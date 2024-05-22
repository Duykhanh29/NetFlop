package com.example.netflop.data.models.TVs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Episode {
    @SerializedName("air_date")
    private String airDate;
    @SerializedName("episode_number")
    private int episodeNumber;
    @SerializedName("episode_type")
    private String episodeType;
    private int id;
    private String name;
    private String overview;
    @SerializedName("production_code")
    private String productionCode;
    private int runtime;
    @SerializedName("season_number")
    private int seasonNumber;
    @SerializedName("show_id")
    private int showID;
    @SerializedName("still_path")
    private String stillPath;
    @SerializedName("vote_average")
    private int voteAverage;
    @SerializedName("vote_count")
    private int voteCount;
    private List<CrewTV> crew;
    @SerializedName("guest_stars")
    private List<GuestStar> guestStars;

    public String getAirDate() {
        return airDate;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getEpisodeType() {
        return episodeType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
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

    public int getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public List<CrewTV> getCrew() {
        return crew;
    }

    public List<GuestStar> getGuestStars() {
        return guestStars;
    }
}
