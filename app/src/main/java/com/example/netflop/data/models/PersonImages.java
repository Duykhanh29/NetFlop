package com.example.netflop.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonImages {
    private int id;
    private List<Profile> profiles;

    public int getID() { return id; }
    public void setID(int value) { this.id = value; }

    public List<Profile> getProfiles() { return profiles; }
    public void setProfiles(List<Profile> value) { this.profiles = value; }
}
