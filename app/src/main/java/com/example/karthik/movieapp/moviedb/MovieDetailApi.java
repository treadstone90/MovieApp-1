package com.example.karthik.movieapp.moviedb;

import android.net.Uri;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

/**
 * Created by karthik on 11/27/15.
 */
public class MovieDetailApi extends Moviedbapi {
    private String apiBaseUri;
    public MovieDetailApi(String apiKey) {
        super(apiKey);
        apiBaseUri = Uri.parse(super.dbBaseUri).buildUpon().appendPath("movie").toString();
    }

    public Uri getUriForMovie(int id) {
        return Uri.parse(apiBaseUri)
                .buildUpon().appendPath(String.valueOf(id))
                .appendQueryParameter(API_KEY, this.getApiKey())
                .appendQueryParameter("append_to_response", "trailers")
                .build();
    }
}

