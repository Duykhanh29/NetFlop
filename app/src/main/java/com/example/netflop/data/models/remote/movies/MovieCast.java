package com.example.netflop.data.models.remote.movies;

import com.example.netflop.constants.enums.MediaType;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCast {
    private Boolean adult;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("genre_ids")
    private List<Integer> genreIDS;
    private int id;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    private String overview;
    private Double popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("release_date")
    private String releaseDate;
    private String title;
    private Boolean video;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("vote_count")
    private int voteCount;
    private String character;
    @SerializedName("credit_id")
    private String creditID;
    private int order;
    @SerializedName("media_type")
    private String mediaType;
    @SerializedName("origin_country")
    private List<String> originCountry;
    @SerializedName("original_name")
    private String originalName;
    @SerializedName("first_air_date")
    private String firstAirDate;
    private String name;
    @SerializedName("episode_count")
    private int episodeCount;

    public MovieCast() {
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public List<Integer> getGenreIDS() {
        return genreIDS;
    }

    public int getId() {
        return id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getVideo() {
        return video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getCharacter() {
        return character;
    }

    public String getCreditID() {
        return creditID;
    }

    public int getOrder() {
        return order;
    }

    public String getMediaType() {
        return mediaType;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public String getName() {
        return name;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }
}
