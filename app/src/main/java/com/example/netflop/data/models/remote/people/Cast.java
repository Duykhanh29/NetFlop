package com.example.netflop.data.models.remote.people;

import com.google.gson.annotations.SerializedName;

public class Cast {
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
    @SerializedName("cast_id")
    private int castID;
    private String character;
    @SerializedName("credit_id")
    private String creditID;
    private int order;
    private String department;
    private String job;
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

    public int getCastID() { return castID; }
    public void setCastID(int value) { this.castID = value; }

    public String getCharacter() { return character; }
    public void setCharacter(String value) { this.character = value; }

    public String getCreditID() { return creditID; }
    public void setCreditID(String value) { this.creditID = value; }

    public int getOrder() { return order; }
    public void setOrder(int value) { this.order = value; }

    public String getDepartment() { return department; }
    public void setDepartment(String value) { this.department = value; }

    public String getJob() { return job; }
    public void setJob(String value) { this.job = value; }
}
