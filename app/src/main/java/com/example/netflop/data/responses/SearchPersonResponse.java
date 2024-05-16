package com.example.netflop.data.responses;

import com.example.netflop.data.models.Person;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchPersonResponse {
    private int page;
    private List<Person> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public SearchPersonResponse(int page, List<Person> results, int totalPages, int totalResults) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public SearchPersonResponse() {
    }

    public int getPage() {
        return page;
    }

    public List<Person> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
