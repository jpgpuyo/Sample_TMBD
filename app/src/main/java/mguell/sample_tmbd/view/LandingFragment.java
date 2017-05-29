package mguell.sample_tmbd.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.guell.mauricio.sample_tmbd.R;

import mguell.sample_tmbd.network.MovieResponse;
import mguell.sample_tmbd.network.RestClient;
import mguell.sample_tmbd.utils.Constants;
import mguell.sample_tmbd.utils.RecyclerViewMargin;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Represents the main fragment, where all the movies are going to be displayed.
 *
 * Created by Mauricio on 17/05/2017.
 */

public class LandingFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private int currentPage;
    private String queryText;

    private final int NUMBER_OF_COLUMNS = 1;

    @BindView(R.id.loading_movies_progress_bar)
    ProgressBar loadingMoviesBar;

    @BindView(R.id.cards_recicler_view)
    RecyclerView cardsRecyclerView;

    @BindString(R.string.query_text_key)
    public String queryTextKey;

    @BindDimen(R.dimen.distance_between_recycler_view_items)
    public int distanceBetweenItems;

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.landing_fragment, container, false);
        ButterKnife.bind(this, view);
        initializePage();
        queryText = getArguments().getString(queryTextKey, "");
        if(queryText.isEmpty()) {
            getMoviesByPopularity();
        } else {
            getMoviesByQuery(queryText);
        }
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        cardsRecyclerView.setLayoutManager(layoutManager);
        cardsRecyclerView.setHasFixedSize(true);
        cardsRecyclerView.setAdapter(new MoviesAdapter());
        cardsRecyclerView.addOnScrollListener(mScrollListener);
        final RecyclerViewMargin decoration = new RecyclerViewMargin(distanceBetweenItems, NUMBER_OF_COLUMNS);
        cardsRecyclerView.addItemDecoration(decoration);
        return view;
    }

    public static LandingFragment newInstance() {
        return new LandingFragment();
    }

    public void initializePage() {
        this.currentPage = 1;
    }

    /**
     * Sets the value of the queryText, updates the displayed list and scrolls it
     * to its top.
     *
     * @param queryText String with the new value of the queryText.
     */
    public void setQueryText(final String queryText) {
        this.queryText = queryText;
        initializePage();
        if (queryText.isEmpty()) {
            getMoviesByPopularity();
        } else {
            getMoviesByQuery(queryText);
        }
        scrollToTop();
    }

    /**
     * ScrollListener that checks if the last item is displayed, if it is, loads another
     * page of movies, by popularity or by query depending on the current type of search.
     */
    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
            if (isLastItemDisplaying()) {
                Log.d(TAG, "Bottom reach");
                currentPage++;
                if(queryText.isEmpty()) {
                    getMoviesByPopularity();
                } else {
                    getMoviesByQuery(queryText);
                }
            }
        }
    };

    /**
     * Computes if the last visible item is displayed.
     *
     * @return true if the last item of the adapter is already displayed, false otherwise.
     */
    private boolean isLastItemDisplaying() {
        if (cardsRecyclerView.getAdapter().getItemCount() != 0) {
            final int lastVisibleItemPosition = ((LinearLayoutManager) cardsRecyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == cardsRecyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    /**
     * Scrolls the recyclerView to its top.
     */
    public void scrollToTop() {
        this.cardsRecyclerView.scrollToPosition(0);
    }

    /**
     * Fills the RecyclerView adapter with the movies with a most similar title
     * to the String param from TMDB.
     *
     * @param text String with the text that is going to be compared to the movie titles.
     */
    public void getMoviesByQuery(final String text) {
        loadingMoviesBar.setVisibility(View.VISIBLE);
        final RestClient.TMDBService service = RestClient.getTMBDService();
        final Call<MovieResponse> call = service.moviesByQuery(Constants.TMBD_API_KEY, text, currentPage);
        Log.d(TAG, "getFilteredMovies call = " + call.request().toString());
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(@NonNull final Call<MovieResponse> call,
                                   @NonNull final Response<MovieResponse> response) {
                Log.d(TAG, "getFilteredMovies Status Code = " + response.code());
                if (response.isSuccessful()) {
                    final MovieResponse result = response.body();
                    if (currentPage == 1) {
                        if(result.getMovies().isEmpty()) {
                            openNoResultsFragment();
                        } else {
                            Log.d(TAG, "getFilteredMovies replacing");
                            ((MoviesAdapter) cardsRecyclerView.getAdapter()).replaceMovies(result.getMovies());
                        }
                    } else {
                        Log.d(TAG, "getFilteredMovies adding");
                        ((MoviesAdapter) cardsRecyclerView.getAdapter()).addMovies(result.getMovies());
                    }
                    loadingMoviesBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull final Call<MovieResponse> call,
                                  @NonNull final Throwable t) {
                loadingMoviesBar.setVisibility(View.GONE);
                openNoInternetFragment();
                t.printStackTrace();
            }
        });
    }

    /**
     * Fills the RecyclerView adapter with the most popular movies from TMDB.
     */
    public void getMoviesByPopularity() {
        loadingMoviesBar.setVisibility(View.VISIBLE);
        final RestClient.TMDBService service = RestClient.getTMBDService();
        final Call<MovieResponse> call = service.moviesByPopularity(Constants.TMBD_API_KEY, currentPage);
        Log.d(TAG, "getMovies call = " + call.request().toString());
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(@NonNull final Call<MovieResponse> call,
                                   @NonNull final Response<MovieResponse> response) {
                Log.d(TAG, "getMovies Status Code = " + response.code());
                if (response.isSuccessful()) {
                    final MovieResponse result = response.body();
                    loadingMoviesBar.setVisibility(View.GONE);
                    if (currentPage == 1) {
                        ((MoviesAdapter) cardsRecyclerView.getAdapter()).replaceMovies(result.getMovies());
                    } else {
                        ((MoviesAdapter) cardsRecyclerView.getAdapter()).addMovies(result.getMovies());
                    }
                    Log.d(TAG, "getMovies response = " + new Gson().toJson(result));
                }
            }

            @Override
            public void onFailure(@NonNull final Call<MovieResponse> call,
                                  @NonNull final Throwable t) {
                loadingMoviesBar.setVisibility(View.GONE);
                openNoInternetFragment();
                t.printStackTrace();
            }
        });
    }

    private void openNoResultsFragment() {
        final NoResultsFragment noResultsFragment = NoResultsFragment.newInstance();
        final Bundle args = new Bundle();
        args.putString(queryTextKey, this.queryText);
        noResultsFragment.setArguments(args);
        try {
            getHostFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, noResultsFragment)
                    .commit();
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void openNoInternetFragment() {
        try {
            getHostFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, NoInternetFragment.newInstance())
                    .commit();
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    public FragmentManager getHostFragmentManager() {
        FragmentManager manager = getFragmentManager();
        if (manager == null && isAdded()) {
            manager = getActivity().getSupportFragmentManager();
        }
        return manager;
    }
}
