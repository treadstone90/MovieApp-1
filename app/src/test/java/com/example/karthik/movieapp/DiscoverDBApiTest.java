package com.example.karthik.movieapp;

import com.example.karthik.movieapp.moviedb.DiscoverMovieDBApi;
import com.example.karthik.movieapp.moviedb.DiscoverMovieDbApiHelper;
import com.example.karthik.movieapp.moviedb.DiscoverResponse;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by karthik on 11/26/15.
 */
public class DiscoverDBApiTest {
    @Test
    public void jsonParsingIsCorrect() throws Exception {
        String json = "{\"page\":1,\"results\":[{\"adult\":false,\"backdrop_path\":\"/kvXLZqY0Ngl1XSw7EaMQO0C1CCj.jpg\"," +
                "\"genre_ids\":[28,12,878],\"id\":102899,\"original_language\":\"en\"" +
                ",\"original_title\":\"Ant-Man\",\"overview\":\"Armed with the astonishing ability to shrink in scale but increase in strength, con-man Scott Lang must embrace his inner-hero and help his mentor, Dr. Hank Pym, " +
                "protect the secret behind his spectacular Ant-Man suit from a new generation of towering threats. " +
                "Against seemingly insurmountable obstacles, Pym and Lang must plan and pull off a heist that will save the world.\"" +
                ",\"release_date\":\"2015-08-14\",\"poster_path\":\"/D6e8RJf2qUstnfkTslTXNTUAlT.jpg\"" +
                ",\"popularity\":68.225126,\"title\":\"Ant-Man\",\"video\":false,\"vote_average\":7.0" +
                ",\"vote_count\":1672}],\"total_pages\":12591,\"total_results\":251808}";

        DiscoverResponse discoverResponse = DiscoverMovieDbApiHelper.fromJsonRepsponse(json);

        assert(discoverResponse.getPage() == 1);
        assert(discoverResponse.getResults().size() == 1);

    }
}
