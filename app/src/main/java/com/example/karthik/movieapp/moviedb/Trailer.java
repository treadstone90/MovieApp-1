package com.example.karthik.movieapp.moviedb;

import java.util.List;

public class Trailer {
    List<String> quicktime;
    List<YouTubeTrailer> youtube;

    public List<YouTubeTrailer> getYouTubeTrailers() {
        return youtube;
    }

    public List<String> getQuickTime() {
        return quicktime;
    }
}
