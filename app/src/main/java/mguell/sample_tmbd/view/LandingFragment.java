package mguell.sample_tmbd.view;

import android.content.Context;
import android.os.Bundle;
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

import javax.inject.Inject;

import mguell.sample_tmbd.App;
import mguell.sample_tmbd.network.MovieResponse;
import mguell.sample_tmbd.network.RestClient;
import mguell.sample_tmbd.presenter.LandingPresenter;
import mguell.sample_tmbd.utils.RecyclerViewMargin;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Inject
    RestClient.TMDBService service;

    @Inject
    Context context;

    private LandingPresenter landingPresenter;

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.landing_fragment, container, false);
        ButterKnife.bind(this, view);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
        this.landingPresenter = new LandingPresenter(this, service);
        initializePage();
        queryText = getArguments().getString(queryTextKey, "");
        if(queryText.isEmpty()) {
            getMoviesByPopularity();
        } else {
            getMoviesByQuery(queryText);
        }
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
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
            if (isLastItemDisplaying() && !loadingMoviesBar.isShown()) {
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
            final LinearLayoutManager layoutManager = ((LinearLayoutManager) cardsRecyclerView.getLayoutManager());
            final int visibleItemCount = layoutManager.getChildCount();
            final int totalItemCount = layoutManager.getItemCount();
            final int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0)
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
        landingPresenter.getMoviesByQuery(text, currentPage);
    }

    /**
     * Fills the RecyclerView adapter with the most popular movies from TMDB.
     */
    public void getMoviesByPopularity() {
        loadingMoviesBar.setVisibility(View.VISIBLE);
        landingPresenter.getMoviesByPopularity(currentPage);
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

    public FragmentManager getHostFragmentManager() {
        FragmentManager manager = getFragmentManager();
        if (manager == null && isAdded()) {
            manager = getActivity().getSupportFragmentManager();
        }
        return manager;
    }

    public void moviesByQueryReady(final MovieResponse moviesByQuery) {
        if (currentPage == 1) {
            if(moviesByQuery.getMovies().isEmpty()) {
                openNoResultsFragment();
            } else {
                Log.d(TAG, "getFilteredMovies replacing");
                ((MoviesAdapter) cardsRecyclerView.getAdapter()).replaceMovies(moviesByQuery.getMovies());
            }
        } else {
            Log.d(TAG, "getFilteredMovies adding");
            ((MoviesAdapter) cardsRecyclerView.getAdapter()).addMovies(moviesByQuery.getMovies());
        }
        loadingMoviesBar.setVisibility(View.GONE);
    }

    public void moviesByPopularityReady(final MovieResponse moviesByPopularity) {
        loadingMoviesBar.setVisibility(View.GONE);
        if (currentPage == 1) {
            ((MoviesAdapter) cardsRecyclerView.getAdapter()).replaceMovies(moviesByPopularity.getMovies());
        } else {
            ((MoviesAdapter) cardsRecyclerView.getAdapter()).addMovies(moviesByPopularity.getMovies());
        }
        Log.d(TAG, "getMovies response = " + new Gson().toJson(moviesByPopularity));
    }

    public void connectionError() {
        try {
            getHostFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, NoInternetFragment.newInstance())
                    .commit();
        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}
