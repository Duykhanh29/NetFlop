package com.example.netflop.data.responses.movies;

import com.example.netflop.data.models.remote.movies.DateModel;
import com.example.netflop.data.models.remote.movies.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NowPlayingResponse {
    private DateModel dates;
    private int page;
    private List<Movie> results;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public NowPlayingResponse(DateModel dates, int page, List<Movie> results, int totalPages, int totalResults) {
        this.dates = dates;
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public NowPlayingResponse() {
    }

    public DateModel getDates() { return dates; }
    public void setDates(DateModel value) { this.dates = value; }

    public int getPage() { return page; }
    public void setPage(int value) { this.page = value; }

    public List<Movie> getResults() { return results; }
    public void setResults(List<Movie> value) { this.results = value; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int value) { this.totalPages = value; }

    public int getTotalResults() { return totalResults; }
    public void setTotalResults(int value) { this.totalResults = value; }
}


