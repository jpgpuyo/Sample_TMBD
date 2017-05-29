package mguell.sample_tmbd.network;

import mguell.sample_tmbd.utils.Constants;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Client of the TMDB API, groups all the methods related to the interaction with it.
 */

public class RestClient {

    private static TMDBService mTMBDService;

    public static TMDBService getTMBDService() {
        if (mTMBDService == null) {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TMDB_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mTMBDService = retrofit.create(TMDBService.class);
        }
        return mTMBDService;
    }

    public interface TMDBService {

        /**
         * Returns the most popular movies.
         *
         * @param apiKey String with the key of the TMDB API.
         * @param page int with the page number that the user want to see.
         * @return MovieResponse call with the list of the most popular at the desired page.
         */
        @Headers("Content-Type: application/json;charset=utf-8")
        @GET("movie/popular")
        Call<MovieResponse> moviesByPopularity(@Query("api_key") String apiKey,
                                               @Query("page") int page);

        /**
         * Returns the movies that more closely match to the query.
         *
         * @param apiKey String with the key of the TMDB API.
         * @param query String with the text that is necessary in the title at this search.
         * @param page int with the page number that the user want to see.
         * @return MovieResponse call with the list of the movies that more closely match to the
         * query at the desired page.
         */
        @Headers("Content-Type: application/json;charset=utf-8")
        @GET("search/movie")
        Call<MovieResponse> moviesByQuery(@Query("api_key") String apiKey,
                                          @Query("query") String query,
                                          @Query("page") int page);
    }
}
