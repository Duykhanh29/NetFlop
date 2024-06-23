package com.example.netflop.data.responses.people;

import com.example.netflop.data.models.remote.people.Person;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularPeopleResponse {
    private int page;
    private List<Person> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public int getPage() { return page; }
    public void setPage(int value) { this.page = value; }

    public PopularPeopleResponse(int page, List<Person> results, int totalPages, int totalResults) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public List<Person> getResults() { return results; }
    public void setResults(List<Person> value) { this.results = value; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int value) { this.totalPages = value; }

    public int getTotalResults() { return totalResults; }
    public void setTotalResults(int value) { this.totalResults = value; }
}
