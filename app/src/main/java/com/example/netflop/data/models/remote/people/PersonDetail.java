package com.example.netflop.data.models.remote.people;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonDetail {
    private Boolean adult;
    @SerializedName("also_known_as")
    private List<String> alsoKnownAs;
    private String biography;
    private String birthday;
    private String deathday;
    private int gender;
    private String homepage;
    private int id;
    private String imdbID;
    @SerializedName("known_for_department")
    private String knownForDepartment;
    private String name;
    @SerializedName("place_of_birth")
    private String placeOfBirth;
    private Double popularity;
    @SerializedName("profile_path")
    private String profilePath;

    public PersonDetail(Boolean adult, List<String> alsoKnownAs, String biography, String birthday, String deathday, int gender, String homepage, int id, String imdbID, String knownForDepartment, String name, String placeOfBirth, Double popularity, String profilePath) {
        this.adult = adult;
        this.alsoKnownAs = alsoKnownAs;
        this.biography = biography;
        this.birthday = birthday;
        this.deathday = deathday;
        this.gender = gender;
        this.homepage = homepage;
        this.id = id;
        this.imdbID = imdbID;
        this.knownForDepartment = knownForDepartment;
        this.name = name;
        this.placeOfBirth = placeOfBirth;
        this.popularity = popularity;
        this.profilePath = profilePath;
    }

    public PersonDetail() {
    }

    public Boolean getAdult() {
        return adult;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public int getGender() {
        return gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public String getName() {
        return name;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
