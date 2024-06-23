package com.example.netflop.data.responses.search;

import com.example.netflop.data.models.remote.movies.SearchMultiModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchMultiResponse {
    private int page;
    private List<SearchMultiModel> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public SearchMultiResponse() {
    }

    public int getPage() {
        return page;
    }

    public List<SearchMultiModel> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
