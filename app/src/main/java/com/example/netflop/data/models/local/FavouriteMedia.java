package com.example.netflop.data.models.local;

import com.example.netflop.constants.enums.SearchType;
import com.example.netflop.constants.enums.TypeOfMedia;

public class FavouriteMedia {
    private int id;
    private int mediaID;
    private TypeOfMedia typeOfMedia;

    private String urlImage;
    private String title;
    private Integer seasonNumber,episodeNumber;

    public FavouriteMedia(int id, int mediaID, TypeOfMedia typeOfMedia, String urlImage, Integer seasonNumber, Integer episodeNumber,String title) {
        this.id = id;
        this.mediaID = mediaID;
        this.typeOfMedia = typeOfMedia;
        this.urlImage = urlImage;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.title=title;
    }

    public FavouriteMedia() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMediaID() {
        return mediaID;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }

    public TypeOfMedia getTypeOfMedia() {
        return typeOfMedia;
    }

    public void setTypeOfMedia(TypeOfMedia typeOfMedia) {
        this.typeOfMedia = typeOfMedia;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
