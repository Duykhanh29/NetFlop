package com.example.netflop.data.models.TVs;

import com.google.gson.annotations.SerializedName;

public class GuestStar {
    private String character;
    @SerializedName("credit_id")
    private String creditID;
    private int order;
    private Boolean adult;
    private int gender;
    private int id;
    @SerializedName("known_for_department")
    private String knownForDepartment;
    private String name;
    @SerializedName("original_name")
    private String originalName;
    private Double popularity;
    @SerializedName("profile_path")
    private String profilePath;

    public String getCharacter() {
        return character;
    }

    public String getCreditID() {
        return creditID;
    }

    public int getOrder() {
        return order;
    }

    public Boolean getAdult() {
        return adult;
    }

    public int getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
