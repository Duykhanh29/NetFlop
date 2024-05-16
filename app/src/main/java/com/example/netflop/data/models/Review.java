package com.example.netflop.data.models;

import com.google.gson.annotations.SerializedName;

public class Review {
    private String author;
    @SerializedName("author_details")
    private AuthorDetails authorDetails;
    private String content;
    @SerializedName("created_at")
    private String createdAt;
    private String id;
    @SerializedName("updated_at")
    private String updatedAt;
    private String url;


    public Review() {
    }

    public Review(String author, AuthorDetails authorDetails, String content, String createdAt, String id, String updatedAt, String url) {
        this.author = author;
        this.authorDetails = authorDetails;
        this.content = content;
        this.createdAt = createdAt;
        this.id = id;
        this.updatedAt = updatedAt;
        this.url = url;
    }

    public String getAuthor() { return author; }
    public void setAuthor(String value) { this.author = value; }

    public AuthorDetails getAuthorDetails() { return authorDetails; }
    public void setAuthorDetails(AuthorDetails value) { this.authorDetails = value; }

    public String getContent() { return content; }
    public void setContent(String value) { this.content = value; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String value) { this.createdAt = value; }

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String value) { this.updatedAt = value; }

    public String getURL() { return url; }
    public void setURL(String value) { this.url = value; }
}
