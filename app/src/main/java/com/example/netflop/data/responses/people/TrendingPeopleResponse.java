package com.example.netflop.data.responses.people;

import com.example.netflop.data.models.remote.people.Person;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrendingPeopleResponse {
    private Long page;
    private List<Person> results;
    @SerializedName("total_pages")
    private Long totalPages;
    @SerializedName("total_results")
    private Long totalResults;

    public TrendingPeopleResponse(Long page, List<Person> results, Long totalPages, Long totalResults) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public TrendingPeopleResponse() {
    }

    public Long getPage() { return page; }
    public void setPage(Long value) { this.page = value; }

    public List<Person> getResults() { return results; }
    public void setResults(List<Person> value) { this.results = value; }

    public Long getTotalPages() { return totalPages; }
    public void setTotalPages(Long value) { this.totalPages = value; }

    public Long getTotalResults() { return totalResults; }
    public void setTotalResults(Long value) { this.totalResults = value; }
}
