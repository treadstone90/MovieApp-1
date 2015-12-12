package com.example.karthik.movieapp.moviedb;

import java.util.List;

public class DiscoverResponse {
    int page;
    List<DiscoverMovieJson> results;
    int total_pages;
    int total_results;

    public int getPage() {
        return page;
    }

    public List<DiscoverMovieJson> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }
}
