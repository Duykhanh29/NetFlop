package com.example.netflop.data.responses.search;

import com.example.netflop.data.models.remote.movies.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchMovieResponse {
        private int page;
        private List<Movie> results;
        @SerializedName("total_pages")
        private int totalPages;
        @SerializedName("total_results")
        private int totalResults;

        public SearchMovieResponse(int page, List<Movie> results, int totalPages, int totalResults) {
            this.page = page;
            this.results = results;
            this.totalPages = totalPages;
            this.totalResults = totalResults;
        }

        public SearchMovieResponse() {
        }

        public int getPage() {
            return page;
        }

        public List<Movie> getResults() {
            return results;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public int getTotalResults() {
            return totalResults;
        }

}
