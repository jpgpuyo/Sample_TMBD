package mguell.sample_tmdb.data.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import mguell.sample_tmdb.data.entity.MovieEntity;

/**
 * Response of the TMDB API calls, with a List of Movie objects.
 */

public class MovieResponse {

    @SerializedName("results")
    private List<MovieEntity> movies;

    /**
     * Gets the Movie List.
     *
     * @return the Movie List, if it does not exist, returns an empty List.
     */
    public List<MovieEntity> getMovies() {
        if (movies == null) {
            return new ArrayList<>(0);
        }
        return movies;
    }
}
