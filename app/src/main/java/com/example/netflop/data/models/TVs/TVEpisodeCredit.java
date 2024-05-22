package com.example.netflop.data.models.TVs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVEpisodeCredit {
    private List<GuestStar> cast;
    private List<CrewTV> crew;
    @SerializedName("guest_stars")
    private List<GuestStar> guestStars;
    private int id;

    public List<GuestStar> getCast() {
        return cast;
    }

    public List<CrewTV> getCrew() {
        return crew;
    }

    public List<GuestStar> getGuestStars() {
        return guestStars;
    }

    public int getId() {
        return id;
    }
}
