package mguell.sample_tmdb.presentation.view.landing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guell.mauricio.sample_tmbd.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import mguell.sample_tmdb.domain.model.Movie;
import mguell.sample_tmdb.presentation.MainActivity;
import mguell.sample_tmdb.presentation.presenter.LandingPresenter;
import mguell.sample_tmdb.presentation.view.BaseFragment;
import mguell.sample_tmdb.presentation.view.adapter.MoviesAdapter;
import mguell.sample_tmdb.utils.Constants;
import mguell.sample_tmdb.utils.RecyclerViewMargin;

/**
 * Represents the main fragment, where all the movies are going to be displayed.
 */

public class LandingFragment extends BaseFragment implements LandingView {

    @BindView(R.id.loading_movies_progress_bar)
    public ProgressBar loadingMoviesBar;
    @BindView(R.id.cards_recycler_view)
    public RecyclerView cardsRecyclerView;
    @BindDimen(R.dimen.distance_between_recycler_view_items)
    public int distanceBetweenItems;
    @BindInt(R.integer.movie_list_column_number)
    int numberOfColumns;

    @Inject
    LandingPresenter landingPresenter;

    /**
     * ScrollListener that checks if the last item is displayed, if it is, loads another
     * page of movies, by popularity or by query depending on the current type of search.
     */
    private final RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull final RecyclerView recyclerView, final int dx, final int dy) {
            landingPresenter.scrollMovieList();
        }
    };

    public static LandingFragment newInstance() {
        return new LandingFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        landingPresenter.setView(this);

        if (getArguments() != null) {
            this.landingPresenter.setQueryText(getArguments().getString(Constants.QUERY_TEXT_KEY, ""));
        } else {
            this.landingPresenter.setQueryText("");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        landingPresenter.setView(null);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.landing_fragment, container, false);
        ButterKnife.bind(this, view);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        cardsRecyclerView.setLayoutManager(layoutManager);
        cardsRecyclerView.setHasFixedSize(true);
        cardsRecyclerView.setAdapter(new MoviesAdapter());
        cardsRecyclerView.addOnScrollListener(mScrollListener);
        final RecyclerViewMargin decoration = new RecyclerViewMargin(distanceBetweenItems, numberOfColumns);
        cardsRecyclerView.addItemDecoration(decoration);
        return view;
    }

    @Override
    public void setLoadingMoviesBarVisibility(final int visibility) {
        loadingMoviesBar.setVisibility(visibility);
    }

    @Override
    public boolean isLoadingMoviesBarVisible() {
        return loadingMoviesBar.isShown();
    }

    @Override
    public void replaceMovies(final List<Movie> movies) {
        ((MoviesAdapter) cardsRecyclerView.getAdapter()).replaceMovies(movies);
    }

    @Override
    public void addMovies(final List<Movie> movies) {
        ((MoviesAdapter) cardsRecyclerView.getAdapter()).addMovies(movies);
    }

    @Override
    public void scrollToTop() {
        cardsRecyclerView.scrollToPosition(0);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return cardsRecyclerView.getLayoutManager();
    }

    @Override
    public int getMoviesCount() {
        return cardsRecyclerView.getAdapter().getItemCount();
    }

    @Override
    public MainActivity getParent() {
        return ((MainActivity) getActivity());
    }

    public void onQueryTextChange(final String queryText) {
        this.landingPresenter.setQueryText(queryText);
    }
}
