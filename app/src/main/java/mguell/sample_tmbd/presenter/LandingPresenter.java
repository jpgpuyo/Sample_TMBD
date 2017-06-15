package mguell.sample_tmbd.presenter;


import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DefaultSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mguell.sample_tmbd.App;
import mguell.sample_tmbd.network.MovieResponse;
import mguell.sample_tmbd.network.RestClient;
import mguell.sample_tmbd.utils.Constants;
import mguell.sample_tmbd.view.LandingFragment;

/**
 * Presenter for managing the LandingFragment api calls.
 */

public class LandingPresenter {

    private final LandingFragment view;

    private final RestClient.TMDBService service;

    private final String TAG = LandingPresenter.class.getSimpleName();

    public LandingPresenter(final LandingFragment view, final RestClient.TMDBService service) {
        this.view = view;
        this.service = service;
    }

    /**
     * Fills the RecyclerView adapter with the movies with a most similar title
     * to the String param from TMDB.
     *
     * @param text String with the text that is going to be compared to the movie titles.
     */
    public void getMoviesByQuery(final String text, final int currentPage) {
        final Observable<MovieResponse> call = service.moviesByQuery(Constants.TMBD_API_KEY, text, currentPage);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {

                    @Override
                    public void onSubscribe(final Disposable d) {
                        Log.i(TAG, "getMoviesByQuery subscribed");
                    }

                    @Override
                    public void onNext(final MovieResponse moviesResponse) {
                        if(moviesResponse != null) {
                            view.moviesByQueryReady(moviesResponse);
                        }
                    }

                    @Override
                    public void onError(final Throwable t) {
                        t.printStackTrace();
                        view.connectionError();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "getMoviesByQuery completed");
                    }
                });
    }

    /**
     * Fills the RecyclerView adapter with the most popular movies from TMDB.
     */
    public void getMoviesByPopularity(final int currentPage) {
        Log.i(TAG, "Cargando página: " + currentPage);
        final Observable<MovieResponse> call = service.moviesByPopularity(Constants.TMBD_API_KEY, currentPage);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResponse>() {

                    @Override
                    public void onSubscribe(final Disposable d) {
                        Log.i(TAG, "getMoviesByPopularity subscribed");
                    }

                    @Override
                    public void onNext(final MovieResponse moviesResponse) {
                        if(moviesResponse != null) {
                            view.moviesByPopularityReady(moviesResponse);
                        }
                    }

                    @Override
                    public void onError(final Throwable t) {
                        t.printStackTrace();
                        view.connectionError();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "getMoviesByPopularity completed");
                    }
                });
    }
}
