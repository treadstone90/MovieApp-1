package com.example.karthik.movieapp.moviedb;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class DiscoverMovieDBApi extends Moviedbapi {

    private String apiBaseUri;
    public DiscoverMovieDBApi(String apiKey) {
        super(apiKey);
        apiBaseUri = Uri.parse(super.dbBaseUri).buildUpon().appendPath("discover")
                .appendPath("movie").toString();
    }

    public List<DiscoverMovieJson> discoverByPopularity(int n) {
        int i = 0;
        int pageNumber = 1;
        ArrayList<DiscoverMovieJson> movieList = new ArrayList<>();
        BriefMovie m = null;
        while ( i < n) {
            DiscoverResponse r = discoverByPagination(pageNumber, "popularity.desc");
            i += r.getTotal_results();
            movieList.addAll(r.getResults());
            pageNumber++;
        }
        return movieList;
    }

    public List<DiscoverMovieJson> discoverByRating(int n) {
        int i = 0;
        int pageNumber = 1;
        ArrayList<DiscoverMovieJson> movieList = new ArrayList<>();
        while ( i < n ) {
            DiscoverResponse r = discoverByPagination(pageNumber, "vote_average.desc");
            i += r.getTotal_results();
            movieList.addAll(r.getResults());
            pageNumber++;
        }
        return movieList;
    }

    DiscoverResponse discoverByPagination(int pageNumber, String sortParam) {
        Uri discover = Uri.parse(apiBaseUri).buildUpon()
                .appendQueryParameter(API_KEY, this.getApiKey())
                .appendQueryParameter("sort_by", sortParam)
                .appendQueryParameter("page", String.valueOf(pageNumber))
                .build();

        String response = getResponse(discover);
        return DiscoverMovieDbApiHelper.fromJsonRepsponse(response);
    }

    DiscoverResponse discoverByReleaseDate(int n) {
        Uri discover = Uri.parse(apiBaseUri).buildUpon()
                .appendQueryParameter(API_KEY, this.getApiKey())
                .appendQueryParameter("append_to_response", "trailers")
                .appendQueryParameter("sort_by", "popularity.desc")
                .build();

        String response = getResponse(discover);
        return DiscoverMovieDbApiHelper.fromJsonRepsponse(response);
    }
}

