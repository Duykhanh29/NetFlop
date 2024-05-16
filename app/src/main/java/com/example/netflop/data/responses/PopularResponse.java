package com.example.netflop.data.responses;

import com.example.netflop.data.models.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularResponse {
    private int page;
    private List<Movie> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public PopularResponse(int page, List<Movie> results, int totalPages, int totalResults) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public PopularResponse() {
    }

    public int getPage() { return page; }
    public void setPage(int value) { this.page = value; }

    public List<Movie> getResults() { return results; }
    public void setResults(List<Movie> value) { this.results = value; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int value) { this.totalPages = value; }

    public int getTotalResults() { return totalResults; }
    public void setTotalResults(int value) { this.totalResults = value; }
}
