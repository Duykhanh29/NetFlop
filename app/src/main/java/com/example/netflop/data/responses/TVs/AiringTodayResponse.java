package com.example.netflop.data.responses.TVs;

import com.example.netflop.data.models.remote.TVs.AiringTodayModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AiringTodayResponse {
    private int page;
    private List<AiringTodayModel> results;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public List<AiringTodayModel> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
