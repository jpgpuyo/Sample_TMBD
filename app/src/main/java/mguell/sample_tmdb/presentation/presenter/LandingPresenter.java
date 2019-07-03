package mguell.sample_tmdb.presentation.presenter;


import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import mguell.sample_tmdb.domain.interactor.GetMoviesByPopularity;
import mguell.sample_tmdb.domain.interactor.GetMoviesByQuery;
import mguell.sample_tmdb.domain.model.Movie;
import mguell.sample_tmdb.presentation.view.landing.LandingView;

/**
 * Presenter for managing the LandingFragment api calls.
 */

public class LandingPresenter {

    private final LandingView view;

    private final String TAG = LandingPresenter.class.getSimpleName();
    private final GetMoviesByPopularity getMoviesByPopularity;
    private final GetMoviesByQuery getMoviesByQuery;
    private int currentPage;
    private String queryText;


    public LandingPresenter(final LandingView view) {
        this.view = view;
        this.getMoviesByPopularity = new GetMoviesByPopularity();
        this.getMoviesByQuery = new GetMoviesByQuery();
        initializePage();
    }

    private void initializePage() {
        this.currentPage = 1;
    }

    private void passPage() {
        this.currentPage += 1;
    }

    private String getQueryText() {
        return queryText;
    }

    /**
     * Sets the value of the queryText, updates the displayed list and scrolls it
     * to its top.
     *
     * @param newText String with the new value of the queryText.
     */
    public void setQueryText(final String newText) {
        initializePage();
        this.queryText = newText;
        if (this.queryText.isEmpty()) {
            showMoviesByPopularity();
        } else {
            showMoviesByQuery(this.queryText);
        }
        scrollToTop();
    }

    /**
     * Computes if the last visible item is displayed.
     *
     * @return true if the last item of the adapter is already displayed, false otherwise.
     */
    private boolean isLastItemDisplaying() {
        if (view.getMoviesCount() != 0) {
            final LinearLayoutManager layoutManager = ((LinearLayoutManager) view.getLayoutManager());
            final int visibleItemCount = layoutManager.getChildCount();
            final int totalItemCount = layoutManager.getItemCount();
            final int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            return (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0;
        }
        return false;
    }

    /**
     * Scrolls the recyclerView to its top.
     */
    private void scrollToTop() {
        view.scrollToTop();
    }

    /**
     * Fills the RecyclerView adapter with the most popular movies from TMDB.
     */
    private void showMoviesByPopularity() {
        view.setLoadingMoviesBarVisibility(View.VISIBLE);
        this.getMoviesByPopularity.execute(new MovieListByPopularityObserver(), currentPage);
    }

    /**
     * Fills the RecyclerView adapter with the movies with a most similar title
     * to the String param from TMDB.
     *
     * @param text String with the text that is going to be compared to the movie titles.
     */
    private void showMoviesByQuery(final String text) {
        view.setLoadingMoviesBarVisibility((View.VISIBLE));
        getMoviesByQuery.execute(new MovieListByQueryObserver(), new GetMoviesByQuery.Params(text, currentPage));
    }

    public void scrollMovieList() {
        if (isLastItemDisplaying() && !view.isLoadingMoviesBarVisible()) {
            passPage();
            if (getQueryText().isEmpty()) {
                showMoviesByPopularity();
            } else {
                showMoviesByQuery(getQueryText());
            }
        }
    }

    private final class MovieListByPopularityObserver extends DisposableObserver<List<Movie>> {

        @Override
        public void onComplete() {
            Log.i(TAG, "showMoviesByPopularity completed");
        }

        @Override
        public void onError(final Throwable e) {
            e.printStackTrace();
            view.getParent().openConnectionError();
        }

        @Override
        public void onNext(final List<Movie> movieList) {
            if (movieList != null) {
                view.setLoadingMoviesBarVisibility(View.GONE);
                if (currentPage == 1) {
                    view.replaceMovies(movieList);
                } else {
                    view.addMovies(movieList);
                }
                Log.d(TAG, "getMovies response = " + new Gson().toJson(movieList));
            }
        }
    }

    private final class MovieListByQueryObserver extends DisposableObserver<List<Movie>> {

        @Override
        public void onComplete() {
            Log.i(TAG, "showMoviesByQuery completed");
        }

        @Override
        public void onError(final Throwable e) {
            Log.e(TAG, e.toString());
            try {
                view.getParent().openConnectionError();
            } catch (final NullPointerException npe) {
                Log.e(TAG, npe.toString());
            }
        }

        @Override
        public void onNext(final List<Movie> movies) {
            if (movies != null) {
                if (currentPage == 1) {
                    if (movies.isEmpty()) {
                        try {
                            view.getParent().openNoResultsFragment(queryText);
                        } catch (final NullPointerException e) {
                            Log.e(TAG, e.toString());
                        }
                    } else {
                        Log.d(TAG, "getFilteredMovies replacing");
                        view.replaceMovies(movies);
                    }
                } else {
                    Log.d(TAG, "getFilteredMovies adding");
                    view.addMovies(movies);
                }
                view.setLoadingMoviesBarVisibility(View.GONE);
            }
        }
    }
}
