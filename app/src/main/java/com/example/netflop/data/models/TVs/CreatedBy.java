package com.example.netflop.data.models.TVs;

import com.google.gson.annotations.SerializedName;

public class CreatedBy {
    private int id;
    @SerializedName("credit_id")
    private String creditID;
    private String name;
    @SerializedName("original_name")
    private String originalName;
    private int gender;
    @SerializedName("profile_path")
    private String profilePath;

    public int getId() {
        return id;
    }

    public String getCreditID() {
        return creditID;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public int getGender() {
        return gender;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
