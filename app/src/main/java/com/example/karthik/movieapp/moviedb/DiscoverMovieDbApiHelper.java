package com.example.karthik.movieapp.moviedb;

import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

public final class DiscoverMovieDbApiHelper {
    public static DiscoverResponse fromJsonRepsponse(String response) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<DiscoverResponse> jsonAdapter = moshi.adapter(DiscoverResponse.class);
        DiscoverResponse  discoverResponse = null;
        Log.i("Helper", response);
        try {
            discoverResponse = jsonAdapter.fromJson(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return discoverResponse;
    }

    public static final String SORT_BY_POP = "POPULARITY_SORT";
    public static final String SORT_BY_RECENT = "RECENT_SORT";
}
