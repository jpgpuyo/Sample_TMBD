package mguell.sample_tmdb.presentation.view.landing;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mguell.sample_tmdb.domain.model.Movie;
import mguell.sample_tmdb.presentation.MainActivity;

public interface LandingView {

    void setLoadingMoviesBarVisibility(final int visibility);

    boolean isLoadingMoviesBarVisible();

    void replaceMovies(final List<Movie> movies);

    void addMovies(final List<Movie> movies);

    RecyclerView.LayoutManager getLayoutManager();

    void scrollToTop();

    int getMoviesCount();

    MainActivity getParent();
}
