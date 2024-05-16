package com.example.netflop.data.responses;

import com.example.netflop.data.models.Review;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {
    private int id;
    private int page;
    private List<Review> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public ReviewResponse(int id, int page, List<Review> results, int totalPages, int totalResults) {
        this.id = id;
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public ReviewResponse() {
    }

    public int getID() { return id; }
    public void setID(int value) { this.id = value; }

    public int getPage() { return page; }
    public void setPage(int value) { this.page = value; }

    public List<Review> getResults() { return results; }
    public void setResults(List<Review> value) { this.results = value; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int value) { this.totalPages = value; }

    public int getTotalResults() { return totalResults; }
    public void setTotalResults(int value) { this.totalResults = value; }
}
