package com.example.karthik.movieapp.moviedb;

import android.net.Uri;

/**
 * Created by karthik on 12/6/15.
 */
public class MovieImageApi extends Moviedbapi {
    private String apiBaseUri;
    public MovieImageApi(String apiKey) {
        super(apiKey);
        this.apiBaseUri = Uri.parse(super.dbBaseUri).buildUpon().appendPath("movie").toString();
    }

    public Uri getUriForMovieImages(int id) {
        return Uri.parse(apiBaseUri).buildUpon()
                .appendPath(String.valueOf(id))
                .appendPath("images")
                .appendQueryParameter(API_KEY, this.getApiKey())
                .build();
    }
}