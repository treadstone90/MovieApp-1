package com.example.karthik.movieapp.moviedb;

import android.net.Uri;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by karthik on 11/25/15.
 */
public class Moviedbapi {
    final String API_KEY = "api_key";
    private String apiKey;
    HttpURLConnection urlConnection = null;
    BufferedReader reader;

    protected final String dbBaseUri = "http://api.themoviedb.org/3";

    public Moviedbapi(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    protected String getResponse(Uri uri) {
        try {
            Log.i("The uri is string", uri.toString());
            URL url = new URL(uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            assert(inputStream != null);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            return  buffer.toString();
        } catch (IOException e) {
            Log.e("Moviedbapi", e.getMessage());
            Log.e("Moviedbapi", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
    }
}

class BriefMovie {
    String title;
    String overview;
    String rating;
    String posterPath;
}


