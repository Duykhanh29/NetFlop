package com.example.netflop.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchMultiModel {
    @SerializedName("backdrop_path")
    private String backdropPath;
    private int id;
    @SerializedName("original_name")
    private String originalName;
    private String overview;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("media_type")
    private String mediaType;
    private Boolean adult;
    private String name;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("genre_ids")
    private List<Integer> genreIDS;
    private Double popularity;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("origin_country")
    private List<String> originCountry;
    private int gender;
    @SerializedName("known_for_department")
    private String knownForDepartment;
    @SerializedName("profile_path")
    private String profilePath;
    @SerializedName("known_for")
    private List<KnownFor> knownFor;
    @SerializedName("original_title")
    private String originalTitle;
    private String title;
    @SerializedName("release_date")
    private String releaseDate;
    private Boolean video;






    public String getBackdropPath() {
        return backdropPath;
    }

    public int getId() {
        return id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getMediaType() {
        return mediaType;
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getName() {
        return name;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public List<Integer> getGenreIDS() {
        return genreIDS;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public int getGender() {
        return gender;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public List<KnownFor> getKnownFor() {
        return knownFor;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Boolean getVideo() {
        return video;
    }
}
