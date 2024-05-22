package com.example.netflop.data.models.TVs;

import com.google.gson.annotations.SerializedName;

public class CrewTV {
    private String department;
    @SerializedName("credit_id")
    private String creditID;
    private Boolean adult;
    private String job;
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

    public String getDepartment() {
        return department;
    }

    public String getCreditID() {
        return creditID;
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getJob() {
        return job;
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
