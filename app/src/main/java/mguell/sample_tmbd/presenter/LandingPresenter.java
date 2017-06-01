package mguell.sample_tmbd.presenter;

import android.support.annotation.NonNull;

import mguell.sample_tmbd.network.MovieResponse;
import mguell.sample_tmbd.network.RestClient;
import mguell.sample_tmbd.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Presenter for managing the LandingFragment api calls.
 */

public class LandingPresenter {

    private final MoviePresenterListener mListener;
    private final RestClient.TMDBService mService;

    public interface MoviePresenterListener {

        void moviesByQueryReady(final MovieResponse moviesByQuery);
        void moviesByPopularityReady(final MovieResponse moviesByPopularity);
        void connectionError();
    }

    public LandingPresenter(final MoviePresenterListener listener){
        this.mListener = listener;
        this.mService = RestClient.getTMBDService();
    }

    /**
     * Fills the RecyclerView adapter with the movies with a most similar title
     * to the String param from TMDB.
     *
     * @param text String with the text that is going to be compared to the movie titles.
     */
    public void getMoviesByQuery(final String text, final int currentPage) {
        final Call<MovieResponse> call = mService.moviesByQuery(Constants.TMBD_API_KEY, text, currentPage);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(@NonNull final Call<MovieResponse> call,
                                   @NonNull final Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    final MovieResponse result = response.body();
                    if(result != null) {
                        mListener.moviesByQueryReady(result);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull final Call<MovieResponse> call,
                                  @NonNull final Throwable t) {
                t.printStackTrace();
                mListener.connectionError();
            }
        });
    }

    /**
     * Fills the RecyclerView adapter with the most popular movies from TMDB.
     */
    public void getMoviesByPopularity(final int currentPage) {
        final Call<MovieResponse> call = mService.moviesByPopularity(Constants.TMBD_API_KEY, currentPage);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(@NonNull final Call<MovieResponse> call,
                                   @NonNull final Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    final MovieResponse result = response.body();
                    if(result != null) {
                        mListener.moviesByPopularityReady(result);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull final Call<MovieResponse> call,
                                  @NonNull final Throwable t) {
                t.printStackTrace();
                mListener.connectionError();
            }
        });
    }
}
