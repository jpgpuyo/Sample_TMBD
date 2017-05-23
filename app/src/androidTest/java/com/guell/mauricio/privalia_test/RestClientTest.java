package com.guell.mauricio.privalia_test;

import android.support.test.runner.AndroidJUnit4;

import com.guell.mauricio.privalia_test.network.MovieResponse;
import com.guell.mauricio.privalia_test.network.RestClient;
import com.guell.mauricio.privalia_test.utils.Constants;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Test for the RestClient methods.
 *
 * Created by Mauricio on 23/05/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RestClientTest {

    private final int VALID_PAGE = 1;
    private final int PAGE_MAX_MOVIES = 20;
    private final int PAGE_ZERO = 0;
    private final int NEGATIVE_PAGE = -1;
    private final String VALID_MOVIE_QUERY = "mage";

    /**
     * Ensures that method moviesByPopularity works correctly if you pass a valid page and
     * api key.
     */
    @Test
    public void getPopularMovies() {
        final RestClient.TMDBService tmbdApi = RestClient.getTMBDService();
        final Call<MovieResponse> call = tmbdApi.moviesByPopularity(
                Constants.TMBD_API_KEY, VALID_PAGE);
        try {
            final Response<MovieResponse> response = call.execute();
            final MovieResponse movieResponse = response.body();
            assertTrue(response.isSuccessful()
                    && movieResponse.getMovies().size() == PAGE_MAX_MOVIES);

        } catch (IOException | NullPointerException e) {
            fail("Should not have throw any exception. " + e.getMessage());
        }
    }

    /**
     * Ensures that method moviesByPopularity fails if you pass an invalid page.
     */
    @Test
    public void getPopularMoviesPageZero() {
        final RestClient.TMDBService tmbdApi = RestClient.getTMBDService();
        final Call<MovieResponse> call = tmbdApi.moviesByPopularity(
                Constants.TMBD_API_KEY, PAGE_ZERO);
        try {
            final Response<MovieResponse> response = call.execute();
            assertFalse(response.isSuccessful());
        } catch (IOException | NullPointerException e) {
            fail("Should not have throw any exception. " + e.getMessage());
        }

    }

    /**
     * Ensures that method moviesByPopularity fails if you pass an invalid page.
     */
    @Test
    public void getPopularMoviesNegativePage() {
        final RestClient.TMDBService tmbdApi = RestClient.getTMBDService();
        final Call<MovieResponse> call = tmbdApi.moviesByPopularity(
                Constants.TMBD_API_KEY, NEGATIVE_PAGE);
        try {
            final Response<MovieResponse> response = call.execute();
            assertFalse(response.isSuccessful());
        } catch (IOException | NullPointerException e) {
            fail("Should not have throw any exception. " + e.getMessage());
        }
    }

    /**
     * Ensures that method moviesByPopularity fails if you pass an invalid page.
     */
    @Test
    public void getPopularMoviesNonExistingPage() {
        final RestClient.TMDBService tmbdApi = RestClient.getTMBDService();
        final Call<MovieResponse> call = tmbdApi.moviesByPopularity(
                Constants.TMBD_API_KEY, Integer.MAX_VALUE);
        try {
            final Response<MovieResponse> response = call.execute();
            assertFalse(response.isSuccessful());
        } catch (IOException | NullPointerException e) {
            fail("Should not have throw any exception. " + e.getMessage());
        }
    }

    /**
     * Ensures that method moviesByPopularity fails if you pass an invalid api key.
     */
    @Test
    public void getPopularMoviesInvalidApiKey() {
        final RestClient.TMDBService tmbdApi = RestClient.getTMBDService();
        final String invalidApiKey = UUID.randomUUID().toString();
        final Call<MovieResponse> call = tmbdApi.moviesByPopularity(
                invalidApiKey, Integer.MAX_VALUE);
        try {
            final Response<MovieResponse> response = call.execute();
            assertFalse(response.isSuccessful());
        } catch (IOException | NullPointerException e) {
            fail("Should not have throw any exception. " + e.getMessage());
        }
    }

    /**
     * Ensures that method moviesByQuery works correctly if you pass a valid query, a valid
     * page and a valid api key.
     */
    @Test
    public void getMoviesByQuery() {
        final RestClient.TMDBService tmbdApi = RestClient.getTMBDService();
        final Call<MovieResponse> call = tmbdApi.moviesByQuery(
                Constants.TMBD_API_KEY, VALID_MOVIE_QUERY, VALID_PAGE);
        try {
            final Response<MovieResponse> response = call.execute();
            final MovieResponse movieResponse = response.body();
            assertTrue(response.isSuccessful()
                    && movieResponse.getMovies().size() == PAGE_MAX_MOVIES);

        } catch (IOException | NullPointerException e) {
            fail("Should not have throw any exception. " + e.getMessage());
        }
    }

    /**
     * Ensures that method moviesByQuery fails if you pass an invalid page.
     */
    @Test
    public void getMoviesByQueryPageZero() {
        final RestClient.TMDBService tmbdApi = RestClient.getTMBDService();
        final Call<MovieResponse> call = tmbdApi.moviesByQuery(
                Constants.TMBD_API_KEY, VALID_MOVIE_QUERY, PAGE_ZERO);
        try {
            final Response<MovieResponse> response = call.execute();
            assertFalse(response.isSuccessful());
        } catch (IOException | NullPointerException e) {
            fail("Should not have throw any exception. " + e.getMessage());
        }
    }

    /**
     * Ensures that method moviesByQuery fails if you pass an invalid page.
     */
    @Test
    public void getMoviesByQueryNegativePage() {
        final RestClient.TMDBService tmbdApi = RestClient.getTMBDService();
        final Call<MovieResponse> call = tmbdApi.moviesByQuery(
                Constants.TMBD_API_KEY, VALID_MOVIE_QUERY, NEGATIVE_PAGE);
        try {
            final Response<MovieResponse> response = call.execute();
            assertFalse(response.isSuccessful());
        } catch (IOException | NullPointerException e) {
            fail("Should not have throw any exception. " + e.getMessage());
        }
    }

    /**
     * Ensures that method moviesByQuery fails if you pass an invalid page.
     */
    @Test
    public void getMoviesByQueryNonExistingPage() {
        final RestClient.TMDBService tmbdApi = RestClient.getTMBDService();
        final Call<MovieResponse> call = tmbdApi.moviesByQuery(
                Constants.TMBD_API_KEY, VALID_MOVIE_QUERY, Integer.MAX_VALUE);
        try {
            final Response<MovieResponse> response = call.execute();
            assertFalse(response.isSuccessful());
        } catch (IOException | NullPointerException e) {
            fail("Should not have throw any exception. " + e.getMessage());
        }
    }

    /**
     * Ensures that method moviesByQuery fails if you pass an invalid api key.
     */
    @Test
    public void getMoviesByQueryInvalidApiKey() {
        final RestClient.TMDBService tmbdApi = RestClient.getTMBDService();
        final String invalidApiKey = UUID.randomUUID().toString();
        final Call<MovieResponse> call = tmbdApi.moviesByQuery(
                invalidApiKey, VALID_MOVIE_QUERY, Integer.MAX_VALUE);
        try {
            final Response<MovieResponse> response = call.execute();
            assertFalse(response.isSuccessful());
        } catch (IOException | NullPointerException e) {
            fail("Should not have throw any exception. " + e.getMessage());
        }
    }

    /**
     * Ensures that method moviesByQuery returns an empty movie list response if you pass
     * a non existing query.
     */
    @Test
    public void getMoviesByNonExistentQuery() {
        final RestClient.TMDBService tmbdApi = RestClient.getTMBDService();
        final String INVALID_MOVIE_QUERY = UUID.randomUUID().toString();
        final Call<MovieResponse> call = tmbdApi.moviesByQuery(
                Constants.TMBD_API_KEY, INVALID_MOVIE_QUERY, VALID_PAGE);
        try {
            final Response<MovieResponse> response = call.execute();
            final MovieResponse movieResponse = response.body();
            assertTrue(response.isSuccessful()
                    && movieResponse.getMovies().size() == 0);

        } catch (IOException | NullPointerException e) {
            fail("Should not have throw any exception. " + e.getMessage());
        }
    }
}
