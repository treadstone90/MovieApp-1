package com.example.karthik.movieapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.karthik.movieapp.R;
import com.example.karthik.movieapp.moviedb.BackDrops;
import com.example.karthik.movieapp.moviedb.MovieApiHelper;
import com.example.karthik.movieapp.moviedb.MovieDetailApi;
import com.example.karthik.movieapp.moviedb.MovieImageApi;
import com.example.karthik.movieapp.moviedb.MovieImageJson;
import com.example.karthik.movieapp.moviedb.MovieJson;
import com.example.karthik.movieapp.moviedb.VolleySingleton;
import com.example.karthik.movieapp.moviedb.YouTubeTrailer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    MovieDetailApi detailApi;
    MovieImageApi imageApi;

    TextView description;
    TextView name;
    TextView runningTime;
    TextView rating;
    YouTubeTrailer youTubeTrailer = null ;

    public MovieDetailActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        detailApi = new MovieDetailApi("0000000");
        imageApi = new MovieImageApi("0000000");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View linearLayout = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        description = (TextView)linearLayout.findViewById(R.id.movie_description);
        name = (TextView)linearLayout.findViewById(R.id.movie_name_text);
        runningTime = (TextView)linearLayout.findViewById(R.id.movie_running_text);
        rating = (TextView)linearLayout.findViewById(R.id.movie_rating_text);
        ViewPager switcher = (ViewPager)linearLayout.findViewById(R.id.imageSwitcher);

        ImagePagerAdapter mPagerAdapter = new ImagePagerAdapter(getContext(), R.layout.movie_back_images);

        description.setText(getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT));

        int movieId = Integer.parseInt(getActivity().getIntent().getStringExtra("movie_id"));

        Uri movieUri = detailApi.getUriForMovie(movieId);
        Uri imageUri = imageApi.getUriForMovieImages(movieId);

        JsonObjectRequest movieJsObjRequest = new JsonObjectRequest(Request.Method.GET,
                movieUri.toString(), null, new MovieDetailListener(), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Unable to connect to server",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        JsonObjectRequest imageJsObjRequest = new JsonObjectRequest(Request.Method.GET,
                imageUri.toString(), null, new MovieImageListener(mPagerAdapter), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NoConnectionError) {
                    Toast.makeText(getContext(), "Unable to connect to server",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        VolleySingleton.getInstance(getContext()).addToRequestQueue(movieJsObjRequest);
        VolleySingleton.getInstance(getContext()).addToRequestQueue(imageJsObjRequest);

        Button trailerButton = (Button)linearLayout.findViewById(R.id.trailer_button);
        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(youTubeTrailer != null) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(youTubeTrailer.getYouTubeIntentString()));
                    if (i.resolveActivity(getContext().getPackageManager()) != null) {
                        startActivity(i);
                    }
                }
            }
        });

        switcher.setAdapter(mPagerAdapter);
        return linearLayout;
    }


    private void onMovieDetailCompletion(MovieJson j) {
        List<YouTubeTrailer> ys = j.getTrailer().getYouTubeTrailers();
        if(ys.size() > 0) {
            youTubeTrailer = ys.get(0);
        }
        name.setText(j.getOriginal_title() + "(" + j.getRelease_date() + ")");
        runningTime.setText(j.getRuntime() + "min");
        rating.setText(j.getAverage() + "/10");
    }

    class MovieDetailListener implements Response.Listener<JSONObject> {
        public MovieDetailListener() {

        }

        @Override
        public void onResponse(JSONObject response) {
            MovieJson j = MovieApiHelper.fromJsonRepsponse(response.toString());
            onMovieDetailCompletion(j);
        }
    }

    class MovieImageListener implements Response.Listener<JSONObject> {

        ImagePagerAdapter imageSwitcher;
        public MovieImageListener(ImagePagerAdapter switcher) {
            this.imageSwitcher = switcher;
        }

        @Override
        public void onResponse(JSONObject response) {
            Log.i("The response is" , response.toString());
            MovieImageJson obj = MovieApiHelper.fromJsonResponseToImageUri(response.toString());
            List<String> imageUris = new ArrayList<>();
            for(BackDrops b: obj.getBackdrops()) {
                imageUris.add("http://image.tmdb.org/t/p/w500/"+b.getFile_path());
            }
            imageSwitcher.addAll(imageUris);
        }
    }
}
