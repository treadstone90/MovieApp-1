package com.example.karthik.movieapp.moviedb;

import java.util.List;
import java.util.Map;

public class MovieJson {
    boolean adult;
    String backdrop_path;
    MovieCollectionJson belongs_to_collection;
    int budget;
    List<Genre> genres;
    String homepage;
    int id;
    String imdb_id;
    String original_language;
    String original_title;
    String overview;
    Float popularity;
    String poster_path;
    List<ProductionCompanies> production_companies;
    List<ProductionCountries> production_countries;
    String release_date;
    long revenue;
    int runtime;
    List<Languages> spoken_languages;
    String status;
    String tagline;
    String title;
    boolean video;
    float vote_average;
    long vote_count;
    Trailer trailers;

    @Override
    public String toString() {
        return this.title + " at " + this.release_date;
    }

    public Trailer getTrailer() {
        return trailers;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getRuntime() {
        return runtime;
    }

    public float getAverage() {
        return vote_average;
    }
}
