package com.example.netflop.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Person {
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
    @SerializedName("known_for")
    private List<KnownFor> knownFor;

    public Boolean getAdult() { return adult; }
    public void setAdult(Boolean value) { this.adult = value; }

    public int getGender() { return gender; }
    public void setGender(int value) { this.gender = value; }

    public int getID() { return id; }
    public void setID(int value) { this.id = value; }

    public String getKnownForDepartment() { return knownForDepartment; }
    public void setKnownForDepartment(String value) { this.knownForDepartment = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getOriginalName() { return originalName; }
    public void setOriginalName(String value) { this.originalName = value; }

    public Double getPopularity() { return popularity; }
    public void setPopularity(Double value) { this.popularity = value; }

    public String getProfilePath() { return profilePath; }
    public void setProfilePath(String value) { this.profilePath = value; }

    public List<KnownFor> getKnownFor() { return knownFor; }
    public void setKnownFor(List<KnownFor> value) { this.knownFor = value; }
}
