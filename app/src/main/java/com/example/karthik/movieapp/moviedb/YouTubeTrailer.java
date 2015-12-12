package com.example.karthik.movieapp.moviedb;

public class YouTubeTrailer {
    String name;
    String size;
    String source;
    String type;

    public String getSource() {
        return source;
    }

    public String getName() {
        return name;
    }

    public String getYouTubeURL() {
        return "http://www.youtube.com/watch?v=" + this.getSource();
    }


    public String getYouTubeIntentString() {
        return "vnd.youtube:" + this.getSource();
    }

    @Override
    public String toString() {
        return "name" + name + " : " + source;
    }
}

