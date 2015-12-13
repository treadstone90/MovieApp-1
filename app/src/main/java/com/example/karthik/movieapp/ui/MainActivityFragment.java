package com.example.karthik.movieapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import com.example.karthik.movieapp.R;
import com.example.karthik.movieapp.moviedb.DiscoverMovieDBApi;
import com.example.karthik.movieapp.moviedb.DiscoverMovieDbApiHelper;
import com.example.karthik.movieapp.moviedb.DiscoverMovieJson;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    DiscoverMovieDBApi api = new DiscoverMovieDBApi("00000000");
    Spinner mSpinner;
    String spinnerState;
    SharedPreferences sharedPref;
    ArrayAdapter<CharSequence> spinnerAdapter;

    ImageAdapter imageAdapter;
    public MainActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        setHasOptionsMenu(true);
    }

    public void onStart() {
        super.onStart();
        spinnerState = sharedPref.getString("sort_state", DiscoverMovieDbApiHelper.SORT_BY_POP);
        Log.i("in the in tsart", spinnerState);
        if(spinnerState == null) {
            spinnerState = DiscoverMovieDbApiHelper.SORT_BY_POP;
        }
    }

    private void loadMovies(String discoverParam) {
        if(isOnline(getContext())) {
            new LoadMovieImageGridTask(api).execute(
                    new LoadGridParams(discoverParam)
            );
        } else {
            Toast.makeText(getContext(), "Unable to connect to server",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.spinner);
        mSpinner = (Spinner) MenuItemCompat.getActionView(item);
        spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.preference_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(new SpinnerListener());
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (!spinnerState.equals(null)) {
            int spinnerPosition = spinnerAdapter.getPosition(spinnerState);
            mSpinner.setSelection(spinnerPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frameLayout = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) frameLayout.findViewById(R.id.movie_grid_view);
        imageAdapter = new ImageAdapter(getContext(), R.layout.movie_poster_image);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DiscoverMovieJson movie = imageAdapter.getItem(position);
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

    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }


    public void onMoviesLoaded(List<DiscoverMovieJson> results) {
        Log.i("MainFragment", "Movie information loaded");
        for(DiscoverMovieJson m: results) {
            m.setPoster_path("http://image.tmdb.org/t/p/w500/"+m.getPoster_path());
        }
        imageAdapter.clear();
        imageAdapter.addAll(results);
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
            SharedPreferences.Editor editor = sharedPref.edit();
            Log.i("in the in listener", selectedItem);
            editor.putString("sort_state", selectedItem);
            editor.commit();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
