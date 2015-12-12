package com.example.karthik.movieapp.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.karthik.movieapp.R;
import com.example.karthik.movieapp.moviedb.DiscoverMovieDBApi;
import com.example.karthik.movieapp.moviedb.DiscoverMovieDbApiHelper;
import com.example.karthik.movieapp.moviedb.DiscoverMovieJson;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    DiscoverMovieDBApi api = new DiscoverMovieDBApi("0000000");

    ImageAdapter adapter;
    public MainActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onStart() {
        super.onStart();
        loadMovies(DiscoverMovieDbApiHelper.SORT_BY_POP);
    }

    private void loadMovies(String discoverParam) {
        new LoadMovieImageGridTask(api).execute(
                new LoadGridParams(discoverParam)
        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.preference_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new SpinnerListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frameLayout = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) frameLayout.findViewById(R.id.movie_grid_view);
        adapter = new ImageAdapter(getContext(), R.layout.movie_poster_image);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DiscoverMovieJson movie= adapter.getItem(position);
                int movieId = movie.getId();
                Intent i = new Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .setClass(getActivity(), MovieDetailActivity.class)
                        .putExtra("movie_id", String.valueOf(movieId))
                        .putExtra(Intent.EXTRA_TEXT, String.valueOf(movie.getOverview()));

                if (i.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });

        return frameLayout;
    }


    public void onMoviesLoaded(List<DiscoverMovieJson> results) {
        Log.i("MainFragment", "Movie information loaded");
        for(DiscoverMovieJson m: results) {
            m.setPoster_path("http://image.tmdb.org/t/p/w500/"+m.getPoster_path());
        }
        adapter.clear();
        adapter.addAll(results);
    }

    class LoadMovieImageGridTask extends AsyncTask<LoadGridParams, Float, List<DiscoverMovieJson>> {
        DiscoverMovieDBApi api;
        public LoadMovieImageGridTask(DiscoverMovieDBApi api) {
            this.api = api;
        }

        @Override
        protected List<DiscoverMovieJson> doInBackground(LoadGridParams... params) {
            LoadGridParams param = params[0];
            if(param.sortPreference.equals(DiscoverMovieDbApiHelper.SORT_BY_POP)) {
                return api.discoverByPopularity(param.moviesPerPage);
            } else {
                return api.discoverByRating(param.moviesPerPage);
            }
        }

        @Override
        protected void onPostExecute(List<DiscoverMovieJson> result) {
            MainActivityFragment.this.onMoviesLoaded(result);
        }
    }

    class LoadGridParams {
        String sortPreference;
        int moviesPerPage = 5;

        public LoadGridParams(String sortPreference) {
            this.sortPreference = sortPreference;
        }
    }

    class SpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedItem = (String)parent.getItemAtPosition(position);
            if(selectedItem.equals(getString(R.string.popularity_preference))) {
                loadMovies(DiscoverMovieDbApiHelper.SORT_BY_POP);
            } else {
                loadMovies(DiscoverMovieDbApiHelper.SORT_BY_RECENT);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
