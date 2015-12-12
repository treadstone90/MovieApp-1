package com.example.karthik.movieapp.moviedb;

import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

final public class MovieApiHelper {
    public static MovieJson fromJsonRepsponse(String response) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<MovieJson> jsonAdapter = moshi.adapter(MovieJson.class);
        MovieJson movieResponse = null;
        Log.i("Helper", response);
        try {
            movieResponse = jsonAdapter.fromJson(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieResponse;
    }

    public static MovieImageJson fromJsonResponseToImageUri(String response) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<MovieImageJson> jsonAdapter = moshi.adapter(MovieImageJson.class);
        MovieImageJson movieResponse = null;
        try {
            movieResponse = jsonAdapter.fromJson(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieResponse;
    }
}
