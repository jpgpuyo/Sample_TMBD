package mguell.sample_tmdb.data.net;

import io.reactivex.Observable;
import mguell.sample_tmdb.data.net.response.MovieResponse;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TMDBService {

    /**
     * Returns the most popular movies.
     *
     * @param apiKey String with the key of the TMDB API.
     * @param page   int with the page number that the user want to see.
     * @return MovieResponse call with the list of the most popular at the desired page.
     */
    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("movie/popular")
    Observable<MovieResponse> moviesByPopularity(@Query("api_key") String apiKey,
                                                 @Query("page") int page);

    /**
     * Returns the movies that more closely match to the query.
     *
     * @param apiKey String with the key of the TMDB API.
     * @param query  String with the text that is necessary in the title at this search.
     * @param page   int with the page number that the user want to see.
     * @return MovieResponse call with the list of the movies that more closely match to the
     * query at the desired page.
     */
    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("search/movie")
    Observable<MovieResponse> moviesByQuery(@Query("api_key") String apiKey,
                                            @Query("query") String query,
                                            @Query("page") int page);
}