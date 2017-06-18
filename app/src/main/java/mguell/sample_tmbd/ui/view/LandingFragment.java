package mguell.sample_tmbd.ui.view;

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

import butterknife.BindInt;
import mguell.sample_tmbd.network.MovieResponse;
import mguell.sample_tmbd.ui.presenter.LandingPresenter;
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

    @BindInt(R.integer.movie_list_column_number)
    int numberOfColumns;

    @BindView(R.id.loading_movies_progress_bar)
    public ProgressBar loadingMoviesBar;

    @BindView(R.id.cards_recicler_view)
    public RecyclerView cardsRecyclerView;

    @BindString(R.string.query_text_key)
    public String queryTextKey;

    @BindDimen(R.dimen.distance_between_recycler_view_items)
    public int distanceBetweenItems;

    private LandingPresenter landingPresenter;

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.landing_fragment, container, false);
        ButterKnife.bind(this, view);
        this.landingPresenter = new LandingPresenter(this);
        this.landingPresenter.setQueryText(getArguments().getString(queryTextKey, ""));
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        cardsRecyclerView.setLayoutManager(layoutManager);
        cardsRecyclerView.setHasFixedSize(true);
        cardsRecyclerView.setAdapter(new MoviesAdapter());
        cardsRecyclerView.addOnScrollListener(mScrollListener);
        final RecyclerViewMargin decoration = new RecyclerViewMargin(distanceBetweenItems, numberOfColumns);
        cardsRecyclerView.addItemDecoration(decoration);
        return view;
    }

    public static LandingFragment newInstance() {
        return new LandingFragment();
    }

    /**
     * ScrollListener that checks if the last item is displayed, if it is, loads another
     * page of movies, by popularity or by query depending on the current type of search.
     */
    public RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
            if (landingPresenter.isLastItemDisplaying() && !loadingMoviesBar.isShown()) {
                landingPresenter.passPage();
                if(landingPresenter.getQueryText().isEmpty()) {
                    landingPresenter.getMoviesByPopularity();
                } else {
                    landingPresenter.getMoviesByQuery(landingPresenter.getQueryText());
                }
            }
        }
    };

    public FragmentManager getHostFragmentManager() {
        FragmentManager manager = getFragmentManager();
        if (manager == null && isAdded()) {
            manager = getActivity().getSupportFragmentManager();
        }
        return manager;
    }

    public LandingPresenter getLandingPresenter() {
        return this.landingPresenter;
    }

}
