package com.example.karthik.movieapp.moviedb;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * Created by karthik on 11/26/15.
 */
public class DiscoverMovieJson {
    boolean adult;
    String backdrop_path;
    List<Integer> genre_ids;
    int id;
    String original_language;
    String original_title;
    String overview;
    String release_date;
    String poster_path;
    Float popularity;
    String title;
    String imdb_id;
    boolean video;
    float average;
    long vote_count;
    @Override
    public String toString() {
        return "DiscoverMovieJson(" + this.title + " at " + this.release_date + ")";
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
