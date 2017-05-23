package com.guell.mauricio.privalia_test.network;

import com.google.gson.annotations.SerializedName;
import com.guell.mauricio.privalia_test.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Response of the TMBD API calls, with a List of Movie objects.
 */

public class MovieResponse {

    @SerializedName("results")
    private List<Movie> movies;

    /**
     * Gets the Movie List.
     *
     * @return the Movie List, if it does not exist, returns an empty List.
     */
    public List<Movie> getMovies() {
        if (movies == null) {
            return new ArrayList<>(0);
        }
        return movies;
    }
}
